package com.msa.payment.exception;

import com.msa.common.exception.CustomException;

public class PaymentNotFoundException extends CustomException {
    public PaymentNotFoundException() {
        super(PaymentErrorCode.PAYMENT_NOT_FOUNT);
    }
}
