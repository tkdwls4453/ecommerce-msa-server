package com.commerce.ecommercemsaserver.test.exception;

import com.msa.common.response.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements StatusCode {

    TEST_1_ERROR_CODE(HttpStatus.BAD_REQUEST, "FTS400", "테스트1 에러입니다."),
    TEST_2_ERROR_CODE(HttpStatus.NOT_FOUND, "FTS401", "테스트2 에러입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
