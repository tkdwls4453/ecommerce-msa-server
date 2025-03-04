package com.msa.common.exception;


import lombok.Getter;

@Getter
public class ExternalRequestException extends RuntimeException {
    String message;

    public ExternalRequestException(String message) {
        this.message = message;
    }
}
