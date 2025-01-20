package com.commerce.ecommercemsaserver.test.exception;

import com.msa.common.exception.CustomException;

public class Test1Exception extends CustomException {

    public Test1Exception() {
        super(TestErrorCode.TEST_1_ERROR_CODE);
    }
}
