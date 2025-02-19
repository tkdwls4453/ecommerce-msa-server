package com.msa.payment.exception;

import com.msa.common.exception.RedisParsingException;
import com.msa.common.response.ApiResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(value = Integer.MIN_VALUE)
@RestControllerAdvice
public class ExternalPaymentExceptionHandler {

    @ExceptionHandler(value = ExternalPaymentErrorException.class)
    public ResponseEntity<ApiResponse<Void>> handleExternalPaymentErrorException(ExternalPaymentErrorException e) {

        log.info("ExternalPaymentErrorException: Code = {}, Message = {}", e.getCode(), e.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.failure(e.getCode(), e.getMessage()));
    }
}
