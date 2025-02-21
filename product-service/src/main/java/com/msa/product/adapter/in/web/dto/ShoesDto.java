package com.msa.product.adapter.in.web.dto;

import com.msa.product.domain.Shoes;
import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.Size;
import lombok.Builder;

@Builder
public record ShoesDto(
    Long shoesId,
    Integer size,
    String color,
    Integer quantity
) {

    public static ShoesDto from(Shoes shoes) {
        return ShoesDto.builder()
            .shoesId(shoes.getShoesId())
            .size(shoes.getSize().getSize())
            .color(shoes.getColor().toString())
            .quantity(shoes.getQuantity().quantity())
            .build();
    }

    public Shoes toDomain(){
        return Shoes.builder()
            .shoesId(shoesId)
            .size(Size.fromInt(size))
            .color(Color.valueOf(color))
            .quantity(new Quantity(quantity))
            .build();
    }
}
