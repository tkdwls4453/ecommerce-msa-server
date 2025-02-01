package com.msa.order.application.port.out;

import com.msa.order.domain.Order;

public interface OrderCreatePort {
    Order save(Order order);
}
