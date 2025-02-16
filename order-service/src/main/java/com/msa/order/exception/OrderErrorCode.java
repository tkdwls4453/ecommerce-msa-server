package com.msa.order.exception;

import com.msa.common.response.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements StatusCode {
    INVALID_TOTAL_PRICE_ERROR(HttpStatus.BAD_REQUEST, "FOD400", "잘못된 주문 금액입니다."),
    INVALID_COUPON_TYPE_ERROR(HttpStatus.BAD_REQUEST, "FOD401", "유효하지 않은 쿠폰 타입입니다."),
    NO_ORDER_ITEM_ERROR(HttpStatus.BAD_REQUEST, "FOD403", "하나 이상의 주문 아이템이 필요합니다."),
    INVALID_COUPON_ERROR(HttpStatus.BAD_REQUEST, "FOD404", "유효하지 않은 쿠폰입니다."),
    OUT_OF_STOCK_ERROR(HttpStatus.BAD_REQUEST, "FOD405", "상품의 재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
