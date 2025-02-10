package com.msa.payment.application.service;

import com.msa.payment.application.port.in.CommandPaymentUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.CommandPaymentPort;
import com.msa.payment.application.port.out.QueryOrderPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService implements CommandPaymentUseCase {

    private final QueryOrderPort queryOrderPort;
    private final CommandPaymentPort commandPaymentPort;

    @Override
    public Payment tryPayment(Long customerId, CreatePaymentCommand command) {
        SimpleOrderResponse simpleOrder = queryOrderPort.findSimpleOrderByOrderId(
            command.orderId());

        Payment initedPayment = Payment.init(customerId, command, simpleOrder);

        return commandPaymentPort.save(initedPayment);
    }
}
