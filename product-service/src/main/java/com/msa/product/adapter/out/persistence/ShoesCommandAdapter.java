package com.msa.product.adapter.out.persistence;

import com.msa.product.application.port.out.ShoesSavePort;
import com.msa.product.application.port.out.ShoesStockManagePort;
import com.msa.product.domain.Shoes;
import com.msa.product.domain.ShoesModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShoesCommandAdapter implements ShoesSavePort, ShoesStockManagePort {

    private final ShoesModelCommandJpaRepository shoesModelCommandJpaRepository;
    private final ShoesCommandJpaRepository shoesCommandJpaRepository;

    @Override
    public ShoesModel save(ShoesModel shoesModel) {
        ShoesModelEntity shoesModelEntity = ShoesModelEntity.from(shoesModel);
        ShoesModelEntity savedShoesModel = shoesModelCommandJpaRepository.save(shoesModelEntity);

        return savedShoesModel.toDomain();
    }

    @Override
    public void updateStock(List<Shoes> shoesList) {
        shoesList.forEach(shoes -> shoesCommandJpaRepository.updateStock(shoes.getShoesId(),
            shoes.getQuantity().quantity()));
    }
}
