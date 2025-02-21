package com.msa.product.adapter.out.persistence;

import com.msa.common.vo.Money;
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
    private List<ShoesEntity> shoesList;

    @Builder
    private ShoesModelEntity(Long modelId, String shoesName, BigDecimal price,
        List<ShoesEntity> shoesList) {
        this.modelId = modelId;
        this.shoesName = shoesName;
        this.price = price;
        this.shoesList = shoesList;
    }

    public static ShoesModelEntity from(ShoesModel shoesModel) {
        return ShoesModelEntity.builder()
            .modelId(shoesModel.getModelId())
            .shoesName(shoesModel.getShoesName().shoesName())
            .price(shoesModel.getPrice().amount())
            .shoesList(shoesModel.getShoesList().stream()
                .map(ShoesEntity::from)
                .toList()
            )
            .build();
    }

    public ShoesModel toDomain() {
        return ShoesModel.builder()
            .modelId(modelId)
            .shoesName(new ShoesName(shoesName))
            .price(new Money(price))
            .shoesList(this.shoesList.stream()
                .map(shoesEntity -> shoesEntity.toDomain())
                .toList()
            )
            .build();
    }
}
