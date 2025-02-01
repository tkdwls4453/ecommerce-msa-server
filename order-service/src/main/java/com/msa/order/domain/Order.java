package com.msa.order.domain;

import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.Money;
import com.msa.order.domain.vo.OrderShoes;
import com.msa.order.domain.vo.ShippingInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
    private Long orderId;
    private final UUID orderCode;
    private final Long customerId;
    private final AppliedCoupon appliedCoupon;
    private ShippingInfo shippingInfo;
    private List<OrderShoes> orderLine;
    private OrderStatus orderStatus;
    private Money totalPrice;
    private LocalDateTime orderTime;


    @Builder
    private Order(Long orderId, UUID orderCode, Long customerId, AppliedCoupon appliedCoupon,
        ShippingInfo shippingInfo, List<OrderShoes> orderLine, OrderStatus orderStatus,
        Money totalPrice, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.appliedCoupon = appliedCoupon;
        this.shippingInfo = shippingInfo;
        this.orderLine = orderLine;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
    }
}
