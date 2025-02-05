package com.msa.order.domain.vo;

import com.msa.common.vo.Money;
import lombok.Builder;

@Builder
public record OrderItem(
    int idx,
    Long itemId,
    String itemName,
    int quantity,
    Money price
) {
}
