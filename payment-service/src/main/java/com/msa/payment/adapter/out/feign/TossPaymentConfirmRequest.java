package com.msa.payment.adapter.out.feign;

import lombok.Builder;

@Builder
public record TossPaymentConfirmRequest(
    String paymentKey,
    String orderId,
    Long amount
) {

}
