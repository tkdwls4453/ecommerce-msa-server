package com.msa.product.domain;

import com.msa.product.domain.vo.Price;
import lombok.Getter;

@Getter
public abstract class ShoesModel {

    private Long modelId;
    private Price price;

    public long getPrice() {
        return price.price();
    }
}