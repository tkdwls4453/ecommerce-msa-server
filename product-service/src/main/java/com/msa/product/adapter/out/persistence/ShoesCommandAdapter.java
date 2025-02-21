package com.msa.product.adapter.out.persistence;

import com.msa.product.application.port.out.ShoesSavePort;
import com.msa.product.domain.ShoesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShoesCommandAdapter implements ShoesSavePort {

    private final ShoesModelCommandJpaRepository shoesModelCommandJpaRepository;

    @Override
    public ShoesModel save(ShoesModel shoesModel) {
        ShoesModelEntity shoesModelEntity = ShoesModelEntity.from(shoesModel);
        ShoesModelEntity savedShoesModel = shoesModelCommandJpaRepository.save(shoesModelEntity);

        return savedShoesModel.toDomain();
    }
}
