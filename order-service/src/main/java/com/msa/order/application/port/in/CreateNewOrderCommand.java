package com.msa.order.application.port.in;

import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.common.vo.Money;
import com.msa.order.domain.vo.OrderItem;
import com.msa.order.domain.vo.ShippingInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateNewOrderCommand(
    List<OrderItem> orderLine,
    AppliedCoupon coupon,
    ShippingInfo shippingInfo,
    Money totalPrice
)
{
    public static CreateNewOrderCommand from(CreateNewOrderRequest request){
        return CreateNewOrderCommand.builder()
            .orderLine(request.orderLine())
            .coupon(request.coupon())
            .shippingInfo(request.shippingInfo())
            .totalPrice(new Money(request.totalAmount()))
            .build();
    }

    public Long getCouponId(){
        return coupon == null ? null : coupon.couponId();
    }
}
