package com.msa.payment.adapter.out;

import com.msa.payment.application.port.out.QueryOrderPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class FakeQueryOrderAdapter implements QueryOrderPort {

    @Override
    public SimpleOrderResponse findSimpleOrderByOrderId(Long orderId) {
        return SimpleOrderResponse.builder().build();
    }
}
