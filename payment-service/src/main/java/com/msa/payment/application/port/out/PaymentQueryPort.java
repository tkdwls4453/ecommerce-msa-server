package com.msa.payment.application.port.out;

import com.msa.payment.domain.Payment;
import java.util.Optional;


public interface PaymentQueryPort {
    Optional<Payment> findById(Long paymentId);
}
