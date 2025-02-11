package com.msa.payment.application.port.in;

import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.domain.Payment;

public interface PaymentCommandUseCase {

    Payment tryPayment(Long customerId, CreatePaymentCommand command);
}
