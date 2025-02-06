package com.msa.order.domain;

import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.common.vo.Money;
import com.msa.order.domain.vo.OrderItem;
import com.msa.order.domain.vo.ShippingInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderFixtures {
    public static final int FIXED_TOTAL_AMOUNT = 415000;
    public static final int PERCENT_TOTAL_AMOUNT = 292600;
    public static final int ORIGINAL_TOTAL_AMOUNT = 418000;
    public static Order order(Long id, OrderStatus status, LocalDateTime orderTime){
        return Order.builder()
            .orderId(id)
            .orderCode(UUID.randomUUID())
            .customerId(1L)
            .orderStatus(status)
            .orderLine(orderLine())
            .appliedCouponId(fixedCoupon().couponId())
            .shippingInfo(shippingInfo())
            .totalPrice(new Money(FIXED_TOTAL_AMOUNT))
            .orderTime(orderTime)
            .build();
    }

    public static CreateNewOrderRequest newOrderWithPercentageCouponRequest() {
        return CreateNewOrderRequest.builder()
            .orderLine(orderLine())
            .coupon(percentageCoupon())
            .shippingInfo(shippingInfo())
            .totalAmount(PERCENT_TOTAL_AMOUNT)
            .build();
    }

    public static CreateNewOrderRequest newOrderWithFixedCouponRequest() {
        return CreateNewOrderRequest.builder()
            .orderLine(orderLine())
            .coupon(fixedCoupon())
            .shippingInfo(shippingInfo())
            .totalAmount(FIXED_TOTAL_AMOUNT)
            .build();
    }

    public static List<OrderItem> orderLine(){
        return List.of(
            new OrderItem(1, 1L, "나이키 에어포스 화이트",  1, new Money(130_000)),
            new OrderItem(2, 2L, "뉴발란스 2002R 그레이", 2, new Money(114_000)),
            new OrderItem(3, 3L, "아디다스 가젤 트리플 블랙", 1, new Money(60_000))
        );
    }

    public static AppliedCoupon fixedCoupon(){
        return new AppliedCoupon(1L, "FIXED", 3000);
    }

    public static AppliedCoupon percentageCoupon(){
        return new AppliedCoupon(2L, "PERCENTAGE", 30);
    }

    public static ShippingInfo shippingInfo(){
        return new ShippingInfo("윤상진", "경기도 고양시 행신동 000-0 3층");
    }
}
