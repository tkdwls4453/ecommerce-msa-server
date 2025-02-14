package com.msa.payment.application.port.out.dto;

import lombok.Builder;

@Builder
public record ExternalPaymentResponse(
    String paymentKey,
    String orderId,
    String status,
    Long totalAmount,
    String approvedAt,
    String method,
    String failMessage
) {

}
