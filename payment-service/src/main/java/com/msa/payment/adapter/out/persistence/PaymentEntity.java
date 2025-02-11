package com.msa.payment.adapter.out.persistence;

import com.msa.common.vo.Money;
import com.msa.payment.domain.PayType;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String orderCode;

    @Column(nullable = false)
    private Long customerId;

    @Column(length = 100)
    private String paymentKey;

    @Column(length = 50)
    private String failReason;

    private boolean cancelYN;

    @Column(length = 255)
    private String cancelReason;

    @Builder
    private PaymentEntity(Long paymentId, PayType payType, PaymentStatus paymentStatus,
        BigDecimal amount, Long orderId, String orderCode, Long customerId, String paymentKey,
        String failReason, boolean cancelYN, String cancelReason) {
        this.paymentId = paymentId;
        this.payType = payType;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.paymentKey = paymentKey;
        this.failReason = failReason;
        this.cancelYN = cancelYN;
        this.cancelReason = cancelReason;
    }

    public static PaymentEntity from(Payment payment) {
        return PaymentEntity.builder()
            .paymentId(payment.getPaymentId())
            .payType(payment.getPayType())
            .paymentStatus(payment.getPaymentStatus())
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

    public Payment toDomain() {
        return Payment.builder()
            .paymentId(paymentId)
            .payType(payType)
            .paymentStatus(paymentStatus)
            .amount(new Money(amount))
            .orderId(orderId)
            .orderCode(orderCode)
            .customerId(customerId)
            .paymentKey(paymentKey)
            .failReason(failReason)
            .cancelYN(cancelYN)
            .cancelReason(cancelReason)
            .build();
    }
}
