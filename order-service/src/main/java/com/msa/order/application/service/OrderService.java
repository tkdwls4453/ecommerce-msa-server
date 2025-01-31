package com.msa.order.application.service;

import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.in.CreateNewOrderUseCase;
import com.msa.order.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements CreateNewOrderUseCase {

    @Override
    public Order createNewOrder(Long userId, CreateNewOrderCommand command) {
        return null;
    }
}
