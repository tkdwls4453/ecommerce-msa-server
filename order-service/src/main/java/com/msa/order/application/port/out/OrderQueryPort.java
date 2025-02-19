package com.msa.order.application.port.out;

import com.msa.order.domain.Order;
import java.util.Optional;

public interface OrderQueryPort {

    Optional<Order> findById(Long orderId);
}
