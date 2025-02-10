package com.msa.product.domain;

import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.ShoesName;
import com.msa.product.domain.vo.Size;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Shoes extends ShoesModel {

    private Long shoesId;
    private ShoesName shoesName;
    private Size size;
    private Color color;
    private Quantity quantity;

    public String getShoesName() {
        return shoesName.shoesName();
    }

    public long getQuantity() {
        return quantity.quantity();
    }

    public boolean isBaseShoes() {
        return Objects.equals(shoesId, super.getModelId());
    }
}
