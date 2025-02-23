package com.msa.product.adapter.in.web.dto;

import com.msa.product.domain.ShoesModel;
import java.util.List;
import lombok.Builder;

@Builder
public record CreatedShoesResponse(
    Long modelId,
    String shoesName,
    Integer price,
    List<ShoesDto> shoesList
){

    public static CreatedShoesResponse from(ShoesModel shoesModel) {
        return CreatedShoesResponse.builder()
            .modelId(shoesModel.getModelId())
            .shoesName(shoesModel.getShoesName().shoesName())
            .price(shoesModel.getPrice().amount().intValue())
            .shoesList(shoesModel.getShoesList().stream()
                .map(ShoesDto::from)
                .toList()
            )
            .build();
    }

}
