package com.msa.payment.exception;

import com.msa.common.exception.CustomException;

public class PriceMismatchException extends CustomException {
    public PriceMismatchException() {
        super(PaymentErrorCode.PRICE_MISMATCH_ERROR);
    }
}
