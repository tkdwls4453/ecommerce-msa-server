package com.msa.payment.domain;

import com.msa.common.vo.Money;

public class PaymentFixtures {

    public static Payment initedPayment(){
        return Payment.builder()
            .paymentId(1L)
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .amount(new Money(30000))
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
}
