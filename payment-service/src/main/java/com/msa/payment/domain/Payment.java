package com.msa.payment.domain;

import com.msa.common.vo.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
    private Long paymentId;
    private PayType payType;
    private Money amount;
    private Long orderId;
    private String orderCode;
    private Long customerId;
    private String paymentKey;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;

    @Builder
    private Payment(Long paymentId, PayType payType, Money amount, Long orderId, String orderCode,
        Long customerId, String paymentKey, String failReason, boolean cancelYN,
        String cancelReason) {
        this.paymentId = paymentId;
        this.payType = payType;
        this.amount = amount;
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.paymentKey = paymentKey;
        this.failReason = failReason;
        this.cancelYN = cancelYN;
        this.cancelReason = cancelReason;
    }

    public String getStringPayType(){
        return this.getPayType() == null ? null : this.getPayType().toString();
    }
}

