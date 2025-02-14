package com.msa.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentQueryJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
