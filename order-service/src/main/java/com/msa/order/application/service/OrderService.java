package com.msa.order.application.service;

import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.in.CreateNewOrderUseCase;
import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.application.port.out.OrderCreatePort;
import com.msa.order.domain.Order;
import com.msa.order.exception.InsufficientStockException;
import com.msa.order.exception.InvalidCouponException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService implements CreateNewOrderUseCase {

    private final DecreaseStockUseCase decreaseStockUseCase;
    private final ApplyCouponUseCase applyCouponUseCase;
    private final OrderCreatePort orderCreatePort;

    @Override
    public Order createNewOrder(Long userId, CreateNewOrderCommand command) {
        LocalDateTime orderTime = LocalDateTime.now();
        Order order = Order.init(userId, command, orderTime, Order.generateOrderCode());

        try{
            decreaseStockUseCase.decreaseStock(order.getOrderLine());
            applyCouponUseCase.applyCoupon(order.getOriginalTotalPrice(), order.getTotalPrice(), order.getAppliedCoupon());
            order.process();
        }catch (InsufficientStockException e){
            order.fail();
            orderCreatePort.save(order);
            throw e;
        }catch (InvalidCouponException e){
            order.fail();
            decreaseStockUseCase.rollback(order.getOrderLine());
            orderCreatePort.save(order);
            throw e;
        }

        return orderCreatePort.save(order);
    }
}
