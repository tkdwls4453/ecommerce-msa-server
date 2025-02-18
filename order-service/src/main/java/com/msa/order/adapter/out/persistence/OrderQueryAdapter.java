package com.msa.order.adapter.out.persistence;

import com.msa.order.application.port.out.OrderQueryPort;
import com.msa.order.domain.Order;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderQueryAdapter implements OrderQueryPort {

    private final OrderQueryJpaRepository orderQueryJpaRepository;
    @Override
    public Optional<Order> findById(Long orderId) {
        Optional<OrderEntity> byId = orderQueryJpaRepository.findById(orderId);

        if(byId.isEmpty()) return Optional.empty();

        OrderEntity orderEntity = byId.get();

        return Optional.of(orderEntity.toDomain());
    }
}
