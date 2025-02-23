package com.msa.product.adapter.out.persistence;

import com.msa.common.vo.Money;
import com.msa.product.domain.Shoes;
import com.msa.product.domain.ShoesModel;
import com.msa.product.domain.vo.ShoesName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shoes_model")
public class ShoesModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;
    private String shoesName;
    private BigDecimal price;

    @OneToMany(mappedBy = "shoesModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ShoesEntity> shoesList = new ArrayList<>();

    @Builder
    private ShoesModelEntity(Long modelId, String shoesName, BigDecimal price) {
        this.modelId = modelId;
        this.shoesName = shoesName;
        this.price = price;
    }

    public static ShoesModelEntity from(ShoesModel shoesModel) {
        ShoesModelEntity shoesModelEntity = ShoesModelEntity.builder()
            .modelId(shoesModel.getModelId())
            .shoesName(shoesModel.getShoesName().shoesName())
            .price(shoesModel.getPrice().amount())
            .build();

        for(Shoes shoes : shoesModel.getShoesList()) {
            shoesModelEntity.addShoe(ShoesEntity.from(shoes));
        }

        return shoesModelEntity;
    }

    public void addShoe(ShoesEntity shoesEntity) {
        this.shoesList.add(shoesEntity);
        shoesEntity.setModel(this);
    }

    public ShoesModel toDomain() {
        return ShoesModel.builder()
            .modelId(modelId)
            .shoesName(new ShoesName(shoesName))
            .price(new Money(price))
            .shoesList(this.shoesList.stream()
                .map(ShoesEntity::toDomain)
                .toList()
            )
            .build();
    }
}
