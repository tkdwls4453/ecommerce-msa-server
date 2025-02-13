package com.msa.payment.exception;

import com.msa.common.response.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements StatusCode {
    PAYMENT_ORDER_INVALID_ERROR(HttpStatus.BAD_REQUEST, "FPY400", "결제 주문이 유효하지 않습니다."),
    PRICE_MISMATCH_ERROR(HttpStatus.BAD_REQUEST, "FPY401", "주문 금액과 결제 금액이 불일치합니다."),
    ORDER_PERMISSION_DENIED_ERROR(HttpStatus.BAD_REQUEST, "FPY402", "주문에 대한 결제 권한이 없습니다."),
    PAYMENT_NOT_FOUNT(HttpStatus.BAD_REQUEST, "FPY403", "존재하지 않는 주문입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
