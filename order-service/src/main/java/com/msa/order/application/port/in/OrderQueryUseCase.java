package com.msa.order.application.port.in;

import com.msa.order.domain.Order;

public interface OrderQueryUseCase {
    Order getOrderById(Long orderId);
}
