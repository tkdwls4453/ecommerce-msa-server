package com.msa.order.adapter.out.persistence;

import com.msa.order.application.port.out.OrderQueryPort;
import com.msa.order.domain.Order;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class OrderQueryAdapter implements OrderQueryPort {

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.empty();
    }
}
