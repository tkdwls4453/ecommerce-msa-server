package com.msa.order.domain;

import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.OrderShoes;
import com.msa.order.domain.vo.ShippingInfo;
import java.util.List;
import java.util.UUID;

public class OrderFixtures {

    public static Order order(){
        return Order.builder()
            .orderId(1L)
            .orderCode(UUID.randomUUID())
            .customerId(1L)
            .orderStatus(OrderStatus.ORDER_RECEIVED)
            .orderLine(orderLine())
            .appliedCoupon(fixedCoupon())
            .shippingInfo(shippingInfo())
            .totalAmount(calculateTotalAmount(orderLine(), fixedCoupon()))
            .build();
    }


    public static CreateNewOrderRequest newOrderWithFixedCouponRequest() {
        return CreateNewOrderRequest.builder()
            .orderLine(orderLine())
            .coupon(fixedCoupon())
            .shippingInfo(shippingInfo())
            .totalAmount(calculateTotalAmount(orderLine(), fixedCoupon()))
            .build();
    }

    public static List<OrderShoes> orderLine(){
        return List.of(
            new OrderShoes(1, 1L, 1L, "나이키 에어포스 화이트", "260", 1, 130_000),
            new OrderShoes(2, 2L, 2L, "뉴발란스 2002R 그레이", "265", 2, 114_000),
            new OrderShoes(3, 3L, 3L, "아디다스 가젤 트리플 블랙", "220", 1, 60_000)
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

    public static int calculateTotalAmount(List<OrderShoes> orderLine, AppliedCoupon coupon){
        int totalItemPrice = orderLine.stream()
            .mapToInt(orderShoes -> orderShoes.price() * orderShoes.quantity())
            .sum();

        int discountAmount = 0;
        if (coupon != null) {
            switch (coupon.type()){
                case "FIXED":
                    discountAmount = coupon.amount();
                    break;
                case "PERCENTAGE":
                    discountAmount = (int)(totalItemPrice * (coupon.amount() / 100.0));
                    break;
            }
        }

        return totalItemPrice - discountAmount;
    }
}
