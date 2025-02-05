package com.msa.order.adapter.in.web.dto;

import com.msa.order.domain.Order;
import com.msa.order.domain.vo.OrderItem;
import com.msa.order.domain.vo.ShippingInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record CreatedNewOrderResponse(
    Long orderId,
    String orderCode,
    String orderStatus,
    List<OrderItem> orderLine,
    ShippingInfo shippingInfo,
    Integer totalPrice
) {

    public static CreatedNewOrderResponse from(Order newOrder) {
        return CreatedNewOrderResponse.builder()
            .orderId(newOrder.getOrderId())
            .orderCode(newOrder.getOrderCode().toString())
            .orderStatus(newOrder.getOrderStatus().toString())
            .orderLine(newOrder.getOrderLine())
            .shippingInfo(newOrder.getShippingInfo())
            .totalPrice(newOrder.getTotalPrice().amount())
            .build();
    }
}
