package com.msa.order.application.port.in;

import com.msa.order.domain.Order;

public interface CreateNewOrderUseCase {
    Order createNewOrder(Long userId, CreateNewOrderCommand command);
}
