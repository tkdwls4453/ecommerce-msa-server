package com.msa.order.adapter.out.persistence;

import com.msa.common.vo.Money;
import com.msa.order.domain.vo.OrderItem;
import jakarta.persistence.Embeddable;
import lombok.Builder;

@Builder
@Embeddable
public record OrderItemEntity(
    int idx,
    Long itemId,
    String itemName,
    int quantity,
    int price
) {


    public static OrderItemEntity from(OrderItem orderItem) {
        return OrderItemEntity.builder()
            .idx(orderItem.idx())
            .itemId(orderItem.itemId())
            .itemName(orderItem.itemName())
            .quantity(orderItem.quantity())
            .price(orderItem.price().amount())
            .build();
    }

    public OrderItem toDomain() {
        return OrderItem.builder()
            .idx(idx)
            .itemId(itemId)
            .itemName(itemName)
            .quantity(quantity)
            .price(new Money(price))
            .build();
    }
}
