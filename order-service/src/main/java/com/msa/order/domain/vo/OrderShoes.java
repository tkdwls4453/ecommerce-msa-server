package com.msa.order.domain.vo;

import lombok.Builder;

@Builder
public record OrderShoes(
    int idx,
    Long modelId,
    Long shoesId,
    String itemName,
    String itemSize,
    int quantity,
    Money price
){
}