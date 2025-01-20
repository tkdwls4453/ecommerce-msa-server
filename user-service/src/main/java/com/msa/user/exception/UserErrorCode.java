package com.msa.user.exception;

import com.msa.common.response.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements StatusCode {
    DUPLICATE_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "FUS400", "중복된 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
