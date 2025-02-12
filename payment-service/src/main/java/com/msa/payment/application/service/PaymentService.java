package com.msa.payment.application.service;

import com.msa.payment.application.port.in.PaymentCommandUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.in.dto.VerifyPaymentCommand;
import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.application.port.out.PaymentQueryPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.domain.Payment;
import com.msa.payment.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentCommandUseCase {

    private final PaymentCommandPort paymentCommandPort;
    private final PaymentQueryPort paymentQueryPort;
    private final OrderQueryPort orderQueryPort;

    @Override
    public Payment verify(Long customerId, VerifyPaymentCommand command) {
        Payment payment = paymentQueryPort.findById(command.orderId())
            .orElseThrow(PaymentNotFoundException::new);

        SimpleOrderResponse order = orderQueryPort.findSimpleOrderByOrderId(command.orderId());

        payment.verifyAndPending(customerId, order, command.paymentKey());
        paymentCommandPort.save(payment);

        return payment;
    }

    @Override
    public Payment tryPayment(Long customerId, CreatePaymentCommand command) {
        Payment initedPayment = Payment.init(customerId, command);

        return paymentCommandPort.save(initedPayment);
    }
}
