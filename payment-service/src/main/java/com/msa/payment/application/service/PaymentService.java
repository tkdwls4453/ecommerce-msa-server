package com.msa.payment.application.service;

import com.msa.payment.application.port.in.PaymentCommandUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentCommandUseCase {

    private final PaymentCommandPort commandPaymentPort;

    @Override
    public Payment tryPayment(Long customerId, CreatePaymentCommand command) {
        Payment initedPayment = Payment.init(customerId, command);

        return commandPaymentPort.save(initedPayment);
    }
}
