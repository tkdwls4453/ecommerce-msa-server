package com.msa.product.exception;

import com.msa.common.exception.CustomException;

public class NotFoundShoesException extends CustomException {
    public NotFoundShoesException() {
        super(ProductErrorCode.NOT_FOUND_SHOES_ERROR);
    }
}
