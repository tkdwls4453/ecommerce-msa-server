package com.msa.payment.adapter.out;

import com.msa.common.vo.Money;
import com.msa.payment.application.port.out.OrderCommandPort;
import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FakeOrderAdapter implements OrderQueryPort, OrderCommandPort {

    @Override
    public SimpleOrderResponse findSimpleOrderByOrderId(Long orderId) {
        return SimpleOrderResponse.builder()
            .orderId(1L)
            .orderCode("test_order_code")
            .customerId(1L)
            .orderStatus("PAYMENT_PENDING")
            .totalPrice(new Money(50000))
            .orderTime(LocalDateTime.now())
            .build();
    }

    @Override
    public void changeToPreparing(Long orderId) {
    }
}
