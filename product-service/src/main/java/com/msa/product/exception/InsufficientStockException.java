package com.msa.product.exception;

import com.msa.common.exception.CustomException;

public class InsufficientStockException extends CustomException {
    public InsufficientStockException() {
        super(ProductErrorCode.OUT_OF_STOCK_ERROR);
    }
}
