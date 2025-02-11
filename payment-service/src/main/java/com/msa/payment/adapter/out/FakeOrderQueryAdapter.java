package com.msa.payment.adapter.out;

import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class FakeOrderQueryAdapter implements OrderQueryPort {

    @Override
    public SimpleOrderResponse findSimpleOrderByOrderId(Long orderId) {
        return SimpleOrderResponse.builder().build();
    }
}
