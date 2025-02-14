package com.msa.payment.domain;

import com.msa.common.vo.Money;
import com.msa.payment.adapter.out.persistence.PaymentEntity;
import java.math.BigDecimal;

public class PaymentFixtures {

    public static Payment initedPayment(){
        return Payment.builder()
            .paymentId(1L)
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .amount(new Money(30000))
            .paymentStatus(PaymentStatus.INITIALIZED)
            .cancelYN(false)
            .build();
    }

    public static Payment verifiedPayment(){
        return Payment.builder()
            .paymentId(1L)
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .paymentKey("test_payment_key")
            .amount(new Money(30000))
            .cancelYN(false)
            .build();
    }

    public static PaymentEntity paymentEntity() {
        return PaymentEntity.builder()
            .paymentId(1L)
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .paymentStatus(PaymentStatus.INITIALIZED)
            .amount(new BigDecimal(30000))
            .cancelYN(false)
            .build();
    }

    public static Payment payment(PaymentStatus paymentStatus, String method, String failReason) {
        return Payment.builder()
            .paymentId(1L)
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .paymentKey("test_payment_key")
            .amount(new Money(30000))
            .paymentStatus(paymentStatus)
            .cancelYN(false)
            .method(method)
            .failReason(failReason)
            .build();
    }
}
