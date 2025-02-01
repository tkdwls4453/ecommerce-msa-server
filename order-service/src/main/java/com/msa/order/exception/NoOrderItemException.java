package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class NoOrderItemException extends CustomException {
    public NoOrderItemException() {
        super(OrderErrorCode.INVALID_COUPON_TYPE_ERROR);
    }
}
