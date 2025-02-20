package com.msa.product.domain;

import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.ShoesName;
import com.msa.product.domain.vo.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Shoes {

    private Long shoesId;
    private Size size;
    private Color color;
    private Quantity quantity;

    @Builder
    private Shoes(Long shoesId, Size size, Color color, Quantity quantity) {
        this.shoesId = shoesId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
