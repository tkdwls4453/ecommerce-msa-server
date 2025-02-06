package com.msa.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCommandJpaRepository extends JpaRepository<OrderEntity,Long> {
}
