package com.msa.order.domain;

import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.common.vo.Money;
import com.msa.order.domain.vo.OrderItem;
import com.msa.order.domain.vo.ShippingInfo;
import com.msa.order.exception.InvalidCouponTypeException;
import com.msa.order.exception.InvalidTotalPriceException;
import com.msa.order.exception.NoOrderItemException;
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
    private final Long appliedCouponId;
    private ShippingInfo shippingInfo;
    private List<OrderItem> orderLine;
    private OrderStatus orderStatus;
    private Money totalPrice;
    private LocalDateTime orderTime;


    @Builder
    private Order(Long orderId, UUID orderCode, Long customerId, Long appliedCouponId,
        ShippingInfo shippingInfo, List<OrderItem> orderLine, OrderStatus orderStatus,
        Money totalPrice, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.appliedCouponId = appliedCouponId;
        this.shippingInfo = shippingInfo;
        this.orderLine = orderLine;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
    }

    public static Order init(Long customerId, CreateNewOrderCommand command, LocalDateTime orderTime, UUID orderCode) {
        verifyTotalPrice(command.orderLine(), command.coupon(), command.totalPrice());

        return Order.builder()
            .orderCode(orderCode)
            .customerId(customerId)
            .appliedCouponId(command.getCouponId())
            .shippingInfo(command.shippingInfo())
            .orderLine(command.orderLine())
            .orderStatus(OrderStatus.ORDER_RECEIVED)
            .totalPrice(command.totalPrice())
            .orderTime(orderTime)
            .build();
     }

    private static void verifyTotalPrice(List<OrderItem> orderLine, AppliedCoupon coupon, Money totalPrice) {
        Money originalTotalPrice = orderLine.stream()
            .map(orderItem -> orderItem.price().multiply(orderItem.quantity()))
            .reduce(Money::add)
            .orElseThrow(NoOrderItemException::new);

        if(coupon == null) {
            if(!originalTotalPrice.equals(totalPrice)) throw new InvalidTotalPriceException();
            return;
        }

        Money disCountPrice = switch (coupon.type()){
            case "FIXED":
                yield  new Money(coupon.amount());
            case "PERCENTAGE":
                yield originalTotalPrice.getPercentDiscountAmount(coupon.amount());
            default:
                throw new InvalidCouponTypeException();
        };

        System.out.println(originalTotalPrice.subtract(disCountPrice));

        if(!originalTotalPrice.subtract(disCountPrice).equals(totalPrice)) throw new InvalidTotalPriceException();

    }

    public static UUID generateOrderCode() {
        return UUID.randomUUID();
    }

    public void process() {
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
    }

    public Money getOriginalTotalPrice() {
        return orderLine.stream()
            .map(orderShoes -> orderShoes.price().multiply(orderShoes.quantity()))
            .reduce(Money::add)
            .orElseThrow(NoOrderItemException::new);
    }

    public void fail() {
        this.orderStatus = OrderStatus.FAILED;
    }
}
