package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class InvalidOrderStatusException extends CustomException {
    public InvalidOrderStatusException() {
        super(OrderErrorCode.NOT_FOUND_ORDER_ERROR);
    }
}
