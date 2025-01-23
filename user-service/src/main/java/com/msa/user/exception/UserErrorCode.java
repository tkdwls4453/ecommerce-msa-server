package com.msa.user.exception;

import com.msa.common.response.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements StatusCode {
    DUPLICATE_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "FUS400", "중복된 이메일입니다."),
    EMAIL_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "FUS404", "이메일을 찾을 수 없습니다."),
    INVALID_LOGIN_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "FUS404", "로그인 정보가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
