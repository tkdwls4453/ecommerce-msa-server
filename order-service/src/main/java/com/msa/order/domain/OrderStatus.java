package com.msa.order.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_RECEIVED("접수됨"),
    PAYMENT_PENDING("결제대기"),
    PREPARING("준비중"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    CANCELED("취소됨"),
    FAILED("실패됨");

    private final String message;
}
