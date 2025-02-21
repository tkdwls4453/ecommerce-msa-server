package com.msa.order.application.port.out;

import com.msa.order.domain.Order;

public interface OrderCommandPort {
    Order save(Order order);
}
