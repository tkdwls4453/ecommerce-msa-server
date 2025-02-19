package com.msa.payment.exception;

import com.msa.common.exception.CustomException;

public class OrderPermissionDeniedException extends CustomException {
    public OrderPermissionDeniedException() {
        super(PaymentErrorCode.ORDER_PERMISSION_DENIED_ERROR);
    }
}
