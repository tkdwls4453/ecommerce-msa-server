package com.msa.payment.application.port.out;

import com.msa.payment.domain.Payment;

public interface CommandPaymentPort {

    Payment save(Payment payment);
}
