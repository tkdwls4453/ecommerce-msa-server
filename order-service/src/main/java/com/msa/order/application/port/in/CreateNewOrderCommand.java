package com.msa.order.application.port.in;

import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.OrderShoes;
import com.msa.order.domain.vo.ShippingInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateNewOrderCommand(
    List<OrderShoes> orderLine,
    AppliedCoupon coupon,
    ShippingInfo shippingInfo,
    Integer totalAmount
)
{
    public static CreateNewOrderCommand from(CreateNewOrderRequest request){
        return CreateNewOrderCommand.builder()
            .orderLine(request.orderLine())
            .coupon(request.coupon())
            .shippingInfo(request.shippingInfo())
            .totalAmount(request.totalAmount())
            .build();
    }
}
