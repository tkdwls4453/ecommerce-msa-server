package com.msa.user.exception;

import com.msa.common.exception.CustomException;

public class EmailNotFoundException extends CustomException {

    public EmailNotFoundException() {
        super(UserErrorCode.EMAIL_NOT_FOUND_ERROR);
    }
}
