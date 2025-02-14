package com.msa.payment.exception;

import com.msa.common.exception.CustomException;

public class PaymentOrderInvalidException extends CustomException {
    public PaymentOrderInvalidException() {
        super(PaymentErrorCode.PAYMENT_ORDER_INVALID_ERROR);
    }
}
