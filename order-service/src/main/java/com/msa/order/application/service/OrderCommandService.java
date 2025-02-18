package com.msa.order.application.service;

import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.in.CreateNewOrderUseCase;
import com.msa.order.application.port.in.PrepareOrderUseCase;
import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.application.port.out.OrderCommandPort;
import com.msa.order.application.port.out.OrderQueryPort;
import com.msa.order.domain.Order;
import com.msa.order.exception.InsufficientStockException;
import com.msa.order.exception.InvalidCouponException;
import com.msa.order.exception.NotFoundOrderException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderCommandService implements CreateNewOrderUseCase, PrepareOrderUseCase {

    private final DecreaseStockUseCase decreaseStockUseCase;
    private final ApplyCouponUseCase applyCouponUseCase;
    private final OrderCommandPort orderCommandPort;
    private final OrderQueryPort orderQueryPort;

    @Override
    public Order createNewOrder(Long userId, CreateNewOrderCommand command) {
        LocalDateTime orderTime = LocalDateTime.now();
        Order order = Order.init(userId, command, orderTime, Order.generateOrderCode());

        try{
            decreaseStockUseCase.decreaseStock(order.getOrderLine());
            applyCouponUseCase.applyCoupon(order.getOriginalTotalPrice(), order.getTotalPrice(), order.getAppliedCouponId());
            order.process();
        }catch (InsufficientStockException e){
            order.fail();
            orderCommandPort.save(order);
            throw e;
        }catch (InvalidCouponException e){
            order.fail();
            decreaseStockUseCase.rollback(order.getOrderLine());
            orderCommandPort.save(order);
            throw e;
        }

        return orderCommandPort.save(order);
    }

    @Override
    public void prepare(Long orderId) {
        Order order = orderQueryPort.findById(orderId).orElseThrow(NotFoundOrderException::new);
        order.prepare();
        orderCommandPort.save(order);
    }
}
