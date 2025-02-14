package com.msa.payment.exception;

import lombok.Getter;

@Getter
public class ExternalPaymentErrorException extends RuntimeException {
    private final String code = "PY500";

    public ExternalPaymentErrorException(String message) {
        super(message);
    }
}
