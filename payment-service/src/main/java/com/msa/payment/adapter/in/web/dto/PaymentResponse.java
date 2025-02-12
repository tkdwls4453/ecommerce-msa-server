package com.msa.payment.adapter.in.web.dto;

import com.msa.payment.domain.Payment;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CreatedPaymentResponse(
    Long paymentId,
    String payType,
    BigDecimal amount,
    Long orderId,
    String orderCode,
    Long customerId,
    String paymentKey,
    String failReason,
    boolean cancelYN,
    String cancelReason
) {

    public static CreatedPaymentResponse from(Payment payment) {
        return CreatedPaymentResponse.builder()
            .paymentId(payment.getPaymentId())
            .payType(payment.getStringPayType())
            .amount(payment.getAmount().amount())
            .orderId(payment.getOrderId())
            .orderCode(payment.getOrderCode())
            .customerId(payment.getCustomerId())
            .paymentKey(payment.getPaymentKey())
            .failReason(payment.getFailReason())
            .cancelYN(payment.isCancelYN())
            .cancelReason(payment.getCancelReason())
            .build();
    }
}
