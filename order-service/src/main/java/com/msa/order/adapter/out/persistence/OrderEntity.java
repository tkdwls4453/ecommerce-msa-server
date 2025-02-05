package com.msa.order.adapter.out.persistence;

import com.msa.order.domain.Order;
import com.msa.order.domain.OrderStatus;
import com.msa.common.vo.Money;
import com.msa.order.domain.vo.ShippingInfo;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String orderCode;

    @Column(nullable = false)
    private Long customerId;

    private Long appliedCouponId;

    private String recipientName;

    @Column(nullable = false)
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_shoes", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemEntity> orderLine;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Builder
    private OrderEntity(Long orderId, String orderCode, Long customerId, Long appliedCouponId,
        String recipientName, String address, List<OrderItemEntity> orderLine,
        OrderStatus orderStatus,
        Integer totalPrice, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.appliedCouponId = appliedCouponId;
        this.recipientName = recipientName;
        this.address = address;
        this.orderLine = orderLine;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
    }

    public static OrderEntity from(Order order) {
        return OrderEntity.builder()
            .orderId(order.getOrderId())
            .orderCode(order.getOrderCode().toString())
            .customerId(order.getCustomerId())
            .appliedCouponId(order.getAppliedCouponId())
            .recipientName(order.getShippingInfo().recipientName())
            .address(order.getShippingInfo().address())
            .orderLine(
                order.getOrderLine().stream()
                    .map(OrderItemEntity::from)
                    .toList()
            )
            .orderStatus(order.getOrderStatus())
            .totalPrice(order.getTotalPrice().amount())
            .orderTime(order.getOrderTime())
            .build();
    }

    public Order toDomain() {
        return Order.builder()
            .orderId(this.orderId)
            .orderCode(UUID.fromString(this.orderCode))
            .customerId(this.customerId)
            .appliedCouponId(this.appliedCouponId)
            .shippingInfo(
                ShippingInfo.builder()
                    .recipientName(this.recipientName)
                    .address(this.address)
                    .build()
            )
            .orderLine(
                orderLine.stream()
                    .map(OrderItemEntity::toDomain)
                    .toList()
            )
            .orderStatus(this.orderStatus)
            .totalPrice(new Money(this.totalPrice))
            .orderTime(this.orderTime)
            .build();
    }


}
