package com.msa.payment.application.port.out.dto;


import lombok.Builder;

@Builder
public record SimpleOrderResponse(
    Long orderId,
    String orderCode,
    Long customerId,
    String orderStatus,
    Integer totalPrice,
    String orderTime
) {

}



