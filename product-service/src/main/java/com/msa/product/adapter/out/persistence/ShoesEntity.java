package com.msa.product.adapter.out.persistence;

import com.msa.product.domain.Shoes;
import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shoes")
public class ShoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoesId;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private ShoesModelEntity shoesModel;

    @Builder
    private ShoesEntity(Long shoesId, Size size, Color color, Integer quantity,
        ShoesModelEntity shoesModel) {
        this.shoesId = shoesId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.shoesModel = shoesModel;
    }

    public static ShoesEntity from(Shoes shoes) {
        return ShoesEntity.builder()
            .shoesId(shoes.getShoesId())
            .size(shoes.getSize())
            .color(shoes.getColor())
            .quantity(shoes.getQuantity().quantity())
            .build();
    }

    public Shoes toDomain() {
        return Shoes.builder()
            .shoesId(shoesId)
            .size(size)
            .color(color)
            .quantity(new Quantity(quantity))
            .build();
    }

    public void setModel(ShoesModelEntity shoesModelEntity) {
        this.shoesModel = shoesModelEntity;
    }
}
