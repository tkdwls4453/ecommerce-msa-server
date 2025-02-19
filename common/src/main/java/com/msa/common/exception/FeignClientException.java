package com.msa.common.exception;


import com.msa.common.response.StatusCode;
import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {
    String message;

    public FeignClientException(String message) {
        this.message = message;
    }
}
