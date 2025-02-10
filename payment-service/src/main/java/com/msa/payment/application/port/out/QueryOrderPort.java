package com.msa.payment.application.port.out;

import com.msa.payment.application.port.out.dto.SimpleOrderResponse;

public interface QueryOrderPort {

    SimpleOrderResponse findSimpleOrderByOrderId(Long orderId);
}
