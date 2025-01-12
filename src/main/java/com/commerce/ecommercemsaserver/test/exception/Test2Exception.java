package com.commerce.ecommercemsaserver.test.exception;

import com.msa.common.exception.CustomException;

public class Test2Exception extends CustomException {

    public Test2Exception() {
        super(TestErrorCode.TEST_2_ERROR_CODE);
    }
}
