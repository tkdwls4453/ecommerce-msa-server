package com.msa.payment.application.port.in;

import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.in.dto.VerifyPaymentCommand;
import com.msa.payment.domain.Payment;

public interface PaymentCommandUseCase {

    Payment verify(Long customerId, VerifyPaymentCommand command);

    Payment tryPayment(Long customerId, CreatePaymentCommand command);
}
