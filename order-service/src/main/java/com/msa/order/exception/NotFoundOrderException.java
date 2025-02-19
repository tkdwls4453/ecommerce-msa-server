package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class NotFoundOrderException extends CustomException {
    public NotFoundOrderException() {
        super(OrderErrorCode.NOT_FOUND_ORDER_ERROR);
    }
}
