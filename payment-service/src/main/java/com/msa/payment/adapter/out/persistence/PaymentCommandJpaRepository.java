package com.msa.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCommandJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
