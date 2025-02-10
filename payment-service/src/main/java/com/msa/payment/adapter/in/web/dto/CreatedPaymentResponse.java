package com.msa.payment.adapter.in.web.dto;

import com.msa.common.vo.Money;
import com.msa.payment.domain.Payment;
import lombok.Builder;

@Builder
public record CreatedPaymentResponse(
    Long paymentId,
    String payType,
    Money amount,
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
            .amount(payment.getAmount())
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
