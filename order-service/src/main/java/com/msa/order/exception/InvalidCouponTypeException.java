package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class InvalidCouponTypeException extends CustomException {
    public InvalidCouponTypeException() {
        super(OrderErrorCode.INVALID_COUPON_TYPE_ERROR);
    }
}
