package com.msa.product.domain;

import com.msa.common.vo.Money;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.adapter.in.web.dto.ShoesDto;
import com.msa.product.domain.vo.ShoesName;
import java.util.ArrayList;
import java.util.List;

public class ProductFixtures {

    public static CreateShoesRequest createShoesRequest(String shoeName, Integer price) {

        return CreateShoesRequest.builder()
            .shoesName(shoeName)
            .price(price)
            .shoesList(shoesDtoList())
            .build();
    }

    public static ShoesModel shoesMode(Long modelId, String shoesName, int price) {

        return ShoesModel.builder()
            .modelId(modelId)
            .shoesName(new ShoesName(shoesName))
            .price(new Money(price))
            .shoesList(shoesDtoList().stream()
                .map(ShoesDto::toDomain)
                .toList()
            )
            .build();
    }
    public static List<ShoesDto> shoesDtoList(){
        List<ShoesDto> shoesDtoList = new ArrayList<>();
        shoesDtoList.add(new ShoesDto(1L, 260, "BLACK", 10));
        shoesDtoList.add(new ShoesDto(2L, 270, "WHITE", 5));
        return shoesDtoList;
    }
}
