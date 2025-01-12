package com.msa.common.exception;


import com.msa.common.response.StatusCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final StatusCode statusCode;

    public CustomException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
