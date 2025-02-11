package com.msa.payment.adapter.out.persistence;

import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentCommandAdapter implements PaymentCommandPort {

    private final PaymentCommandJpaRepository paymentCommandJpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntity.from(payment);
        return paymentCommandJpaRepository.save(paymentEntity).toDomain();
    }
}
