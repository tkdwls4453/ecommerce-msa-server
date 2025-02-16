package com.msa.payment.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VerifyPaymentRequest(
    @NotNull(message = "결제 아이디는 필수입니다.")
    Long paymentId,

    @NotNull(message = "주문 아이디는 필수입니다.")
    Long orderId,

    @NotNull(message = "결제 키는 필수입니다.")
    String paymentKey
) {

}
