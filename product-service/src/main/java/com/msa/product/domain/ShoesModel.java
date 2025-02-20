package com.msa.product.domain;

import com.msa.common.vo.Money;
import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.domain.vo.ShoesName;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShoesModel {
    private Long modelId;
    private ShoesName shoesName;
    private Money price;
    private List<Shoes> shoesList;

    @Builder
    private ShoesModel(Long modelId, ShoesName shoesName, Money price, List<Shoes> shoesList) {
        this.modelId = modelId;
        this.shoesName = shoesName;
        this.price = price;
        this.shoesList = shoesList;
    }

    public static ShoesModel generate(CreateShoesCommand command) {
        return ShoesModel.builder()
            .shoesName(command.shoesName())
            .price(command.price())
            .shoesList(command.shoesList())
            .build();
    }

}