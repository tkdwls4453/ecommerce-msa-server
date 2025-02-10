package com.msa.payment.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreatePaymentRequest(
    @NotNull(message = "주문 아이디는 필수입니다.")
    Long orderId,

    @NotNull(message = "결제 금액 정보는 필수입니다.")
    Integer amount
) {

}
