package com.msa.common.response;

public enum ResponseStatus {
    SUCCESS("성공"),
    FAIL("실패");

    private final String message;

    ResponseStatus(String message) {
        this.message = message;
    }
}
