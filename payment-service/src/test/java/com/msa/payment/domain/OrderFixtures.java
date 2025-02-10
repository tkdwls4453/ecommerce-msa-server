package com.msa.payment.domain;

import com.msa.common.vo.Money;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;

public class OrderFixtures {

    public static SimpleOrderResponse simpleOrderResponse(){
        return SimpleOrderResponse.builder()
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .orderStatus("PAYMENT_PENDING")
            .totalPrice(new Money(30000))
            .build();
    }

    public static SimpleOrderResponse invalidSimpleOrderResponse(){
        return SimpleOrderResponse.builder()
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .orderStatus("ORDER_RECEIVED")
            .totalPrice(new Money(30000))
            .build();
    }
}
