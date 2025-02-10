package com.msa.payment.adapter.out.persistence;

import com.msa.payment.application.port.out.CommandPaymentPort;
import com.msa.payment.domain.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class CommandPaymentAdapter implements CommandPaymentPort {

    @Override
    public Payment save(Payment payment) {
        return null;
    }
}
