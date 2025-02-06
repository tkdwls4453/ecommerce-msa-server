package com.msa.order.adapter.in.web.dto;

import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.OrderItem;
import com.msa.order.domain.vo.ShippingInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateNewOrderRequest(
    @NotNull(message = "주문 아이템 정보는 필수입니다.")
    @Size(min = 1, message = "최소 1개 이상의 아이템을 주문해야 합니다.")
    List<OrderItem> orderLine,

    AppliedCoupon coupon,

    @NotNull(message = "배송 정보는 필수입니다.")
    ShippingInfo shippingInfo,

    @PositiveOrZero(message = "주문 금액은 0 이상입니다.")
    Integer totalAmount
) {
}
