package com.msa.order.adapter.in.web.dto;

import com.msa.order.domain.Order;
import java.time.LocalDateTime;
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

    public static SimpleOrderResponse from(Order order) {
        return SimpleOrderResponse.builder()
            .orderId(order.getOrderId())
            .orderCode(order.getOrderCode().toString())
            .customerId(order.getCustomerId())
            .orderStatus(order.getOrderStatus().toString())
            .totalPrice(order.getTotalPrice().amount().intValue())
            .orderTime(order.getOrderTime().toString())
            .build();
    }
}
