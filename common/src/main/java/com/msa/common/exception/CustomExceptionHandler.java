package com.msa.common.exception;

import com.msa.common.response.ApiResponse;
import com.msa.common.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(value = Integer.MIN_VALUE)
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ApiResponse<Void>> customException(CustomException e){
        StatusCode statusCode = e.getStatusCode();

        log.info("CustomException: Code = {}, Message = {}", statusCode.getCode(), statusCode.getMessage());

        return ResponseEntity
            .status(statusCode.getHttpStatus())
            .body(ApiResponse.failure(statusCode));
    }
}
