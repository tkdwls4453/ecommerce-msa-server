package com.msa.user.exception;

import com.msa.common.exception.CustomException;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super(UserErrorCode.DUPLICATE_EMAIL_ERROR);
    }
}
