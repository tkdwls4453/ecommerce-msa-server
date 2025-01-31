package com.msa.order.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppliedCoupon(
    @NotNull(message = "적용 쿠폰 아이디는 필수입니다.")
    Long couponId,
    @NotBlank(message = "쿠폰 타입은 필수입니다.")
    String type,
    @NotNull(message = "할인 금액 또는 비율은 필수입니다.")
    Integer amount
){
}