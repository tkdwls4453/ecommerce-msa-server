package com.msa.product.adapter.out.persistence;

import com.msa.product.domain.vo.*;
import jakarta.persistence.*;

@Entity
public class ShoesEntity {

    private Long modelId;
    @Embedded
    private Price price;

    @Id
    @GeneratedValue
    private Long shoesId;
    @Embedded
    private ShoesName shoesName;
    private Size size;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Embedded
    private Quantity quantity;

    public ShoesEntity(
            Long modelId,
            Price price,
            Long shoesId,
            ShoesName shoesName,
            Size size,
            Color color,
            Quantity quantity
    ) {
        this.modelId = modelId;
        this.price = price;
        this.shoesId = shoesId;
        this.shoesName = shoesName;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
