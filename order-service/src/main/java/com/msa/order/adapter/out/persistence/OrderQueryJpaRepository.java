package com.msa.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderQueryJpaRepository extends JpaRepository<OrderEntity, Long> {

}
