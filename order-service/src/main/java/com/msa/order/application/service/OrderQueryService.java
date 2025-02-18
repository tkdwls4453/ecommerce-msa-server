package com.msa.order.application.service;


import com.msa.order.application.port.in.OrderQueryUseCase;
import com.msa.order.application.port.out.OrderQueryPort;
import com.msa.order.domain.Order;
import com.msa.order.exception.NotFoundOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderQueryService implements OrderQueryUseCase {

    private final OrderQueryPort orderQueryPort;

    @Override
    public Order getOrderById(Long orderId) {
        if(orderId == null) {
            throw new NotFoundOrderException();
        }

        return orderQueryPort.findById(orderId)
            .orElseThrow(NotFoundOrderException::new);
    }
}
