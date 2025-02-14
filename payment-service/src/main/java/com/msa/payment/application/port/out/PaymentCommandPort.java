package com.msa.payment.application.port.out;

import com.msa.payment.domain.Payment;

public interface PaymentCommandPort {

    Payment save(Payment payment);

}
