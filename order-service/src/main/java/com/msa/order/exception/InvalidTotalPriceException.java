package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class InvalidTotalPriceException extends CustomException {
    public InvalidTotalPriceException() {
        super(OrderErrorCode.INVALID_TOTAL_PRICE_ERROR);
    }
}
