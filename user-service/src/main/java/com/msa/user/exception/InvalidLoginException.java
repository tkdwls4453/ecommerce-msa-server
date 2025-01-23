package com.msa.user.exception;

import com.msa.common.exception.CustomException;

public class InvalidLoginException extends CustomException {

    public InvalidLoginException() {
        super(UserErrorCode.INVALID_LOGIN_REQUEST_ERROR);
    }
}
