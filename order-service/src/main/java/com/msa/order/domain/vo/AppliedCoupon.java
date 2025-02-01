package com.msa.order.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AppliedCoupon(
    @NotNull(message = "적용 쿠폰 아이디는 필수입니다.")
    Long couponId,
    @NotBlank(message = "쿠폰 타입은 필수입니다.")
    String type, // FIXED, PERCENTAGE
    @NotNull(message = "할인 금액 또는 비율은 필수입니다.")
    Integer amount
){
}