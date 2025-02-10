package com.msa.payment.application.service;

import com.msa.payment.application.port.in.CommandPaymentUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.domain.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements CommandPaymentUseCase {

    @Override
    public Payment tryPayment(Long customerId, CreatePaymentCommand command) {
        return null;
    }
}
