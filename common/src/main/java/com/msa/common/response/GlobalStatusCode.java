package com.msa.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalStatusCode implements StatusCode {

    SUCCESS(HttpStatus.OK, "S200", "성공"),
    FAIL(HttpStatus.BAD_REQUEST, "F400", "잘못된 사용자 요청"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E500", "서버 내부 에러"),
    REDIS_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "R500", "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
