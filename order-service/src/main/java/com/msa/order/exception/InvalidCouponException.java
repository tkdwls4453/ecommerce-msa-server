package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class InvalidCouponException extends CustomException {
    public InvalidCouponException() {
        super(OrderErrorCode.INVALID_COUPON_ERROR);
    }
}
