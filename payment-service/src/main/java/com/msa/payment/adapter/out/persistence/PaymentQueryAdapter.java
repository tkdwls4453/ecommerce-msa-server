package com.msa.payment.adapter.out.persistence;

import com.msa.payment.application.port.out.PaymentQueryPort;
import com.msa.payment.domain.Payment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentQueryAdapter implements PaymentQueryPort {

    private final PaymentQueryJpaRepository paymentQueryJpaRepository;

    @Override
    public Optional<Payment> findById(Long paymentId) {
        Optional<PaymentEntity> byId = paymentQueryJpaRepository.findById(paymentId);

        return byId.map(PaymentEntity::toDomain);
    }
}
