package com.msa.payment.application.port.in.dto;

import com.msa.payment.adapter.in.web.dto.VerifyPaymentRequest;
import lombok.Builder;

@Builder
public record VerifyPaymentCommand(
    Long paymentId,
    Long orderId,
    String paymentKey
) {

    public static VerifyPaymentCommand from(VerifyPaymentRequest request) {
        return VerifyPaymentCommand.builder()
            .orderId(request.orderId())
            .paymentId(request.paymentId())
            .paymentKey(request.paymentKey())
            .build();
    }
}
