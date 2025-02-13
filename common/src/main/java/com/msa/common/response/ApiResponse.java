package com.msa.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        return createApiResponse(GlobalStatusCode.SUCCESS, GlobalStatusCode.SUCCESS.getCode(), message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return createApiResponse(GlobalStatusCode.SUCCESS, GlobalStatusCode.SUCCESS.getCode(), null, data, null);
    }

    public static ApiResponse<Void> success(String message) {
        return createApiResponse(GlobalStatusCode.SUCCESS, GlobalStatusCode.SUCCESS.getCode(), message, null, null);
    }

    public static ApiResponse<Void> success() {
        return createApiResponse(GlobalStatusCode.SUCCESS, GlobalStatusCode.SUCCESS.getCode(), null, null, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> failure(String code, String message) {
        return createApiResponse(GlobalStatusCode.FAIL, code, message, null, null);
    }

    public static <T> ApiResponse<T> failure(T errors) {
        return createApiResponse(GlobalStatusCode.FAIL, GlobalStatusCode.FAIL.getCode(), null, null, errors);
    }

    public static ApiResponse<Void> failure(StatusCode code) {
        return createApiResponse(GlobalStatusCode.FAIL, code.getCode(), code.getMessage(), null, null);
    }

    // 에러 응답
    public static ApiResponse<Void> error() {
        return createApiResponse(GlobalStatusCode.ERROR, GlobalStatusCode.ERROR.getCode(), GlobalStatusCode.ERROR.getMessage(), null, null);
    }

    private static <T> ApiResponse<T> createApiResponse(StatusCode responseStatus, String code, String message, T data, T errors) {
        return ApiResponse.<T>builder()
            .status(responseStatus.toString())
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
