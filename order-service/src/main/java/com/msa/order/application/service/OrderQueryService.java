package com.msa.order.application.service;


import com.msa.order.application.port.in.OrderQueryUseCase;
import com.msa.order.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryService implements OrderQueryUseCase {

    @Override
    public Order getOrderById(Long orderId) {
        return null;
    }
}
