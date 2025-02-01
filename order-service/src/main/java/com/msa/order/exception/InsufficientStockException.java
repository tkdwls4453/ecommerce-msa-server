package com.msa.order.exception;

import com.msa.common.exception.CustomException;

public class InsufficientStockException extends CustomException {
    public InsufficientStockException() {
        super(OrderErrorCode.OUT_OF_STOCK_ERROR);
    }
}
