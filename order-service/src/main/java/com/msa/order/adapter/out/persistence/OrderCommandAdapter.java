package com.msa.order.adapter.out.persistence;

import com.msa.order.application.port.out.OrderCreatePort;
import com.msa.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderCommandAdapter implements OrderCreatePort {

    private final OrderCommandJpaRepository orderCommandJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderEntity.from(order);
        return orderCommandJpaRepository.save(orderEntity).toDomain();
    }

}