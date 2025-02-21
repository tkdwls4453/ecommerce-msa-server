package com.msa.payment.adapter.in.web.dto;

import com.msa.payment.domain.Payment;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PaymentResponse(
    Long paymentId,
    String method,
    String status,
    BigDecimal amount,
    Long orderId,
    String orderCode,
    Long customerId,
    String paymentKey,
    String failReason,
    boolean cancelYN,
    String cancelReason
) {

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
            .paymentId(payment.getPaymentId())
            .method(payment.getMethod())
            .status(payment.getPaymentStatusAsString())
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
