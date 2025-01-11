package com.msa.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final String status;
    private final String code;
    private final String message;
    private final T data;
    private final T errors;

    @Builder
    private ApiResponse(String status, String code, String message, T data, T errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return createApiResponse(ResponseStatus.SUCCESS.toString(), "S200", message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return createApiResponse(ResponseStatus.SUCCESS.toString(), "S200", "OK", data, null);
    }

    public static ApiResponse<Void> success(String message) {
        return createApiResponse(ResponseStatus.SUCCESS.toString(), "S200", message, null, null);
    }

    public static ApiResponse<Void> success() {
        return createApiResponse(ResponseStatus.SUCCESS.toString(), "S200", "OK", null, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> failure(String code, String message, T errors) {
        return createApiResponse(ResponseStatus.FAIL.toString(), code, message, null, errors);
    }

    public static <T> ApiResponse<T> failure(String code, T errors) {
        return createApiResponse(ResponseStatus.FAIL.toString(), code, null, null, errors);
    }

    public static ApiResponse<Void> failure(String code, String message) {
        return createApiResponse(ResponseStatus.FAIL.toString(), code, message, null, null);
    }

    private static <T> ApiResponse<T> createApiResponse(String status, String code, String message, T data, T errors) {
        return ApiResponse.<T>builder()
            .status(status)
            .code(code)
            .message(message)
            .data(data)
            .errors(errors)
            .build();
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
            "status='" + status + '\'' +
            ", code='" + code + '\'' +
            ", message='" + message + '\'' +
            ", data=" + data +
            ", errors=" + errors +
            '}';
    }
}
