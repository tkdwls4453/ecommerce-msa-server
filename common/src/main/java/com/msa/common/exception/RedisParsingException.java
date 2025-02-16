package com.msa.common.exception;

import com.msa.common.response.GlobalStatusCode;
import lombok.Getter;

@Getter
public class RedisParsingException extends CustomException {

    private final String message;
    private String value;
    private Class<?> clazz;

    public RedisParsingException(Exception e) {
        super(GlobalStatusCode.REDIS_PARSING_ERROR);
        this.message = e.getMessage();
    }

    public<T> RedisParsingException(Exception e, String value, Class<T> clazz) {
        super(GlobalStatusCode.REDIS_PARSING_ERROR);
        this.message = e.getMessage();
        this.value = value;
        this.clazz = clazz;
    }

}
