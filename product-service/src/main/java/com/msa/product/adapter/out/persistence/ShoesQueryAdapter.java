package com.msa.product.adapter.out.persistence;

import com.msa.product.application.port.out.ShoesQueryPort;
import com.msa.product.domain.Shoes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShoesQueryAdapter implements ShoesQueryPort {

    private final ShoesQueryJpaRepository shoesQueryJpaRepository;

    @Override
    public List<Shoes> findByIdIn(List<Long> idList) {
        List<ShoesEntity> shoesEntityList = shoesQueryJpaRepository.findAllById(idList);

        return shoesEntityList.stream()
            .map(ShoesEntity::toDomain).toList();
    }

    @Override
    public List<Shoes> findByShoesIdInWithPessimisticLock(List<Long> idList) {
        List<ShoesEntity> shoesEntityList = shoesQueryJpaRepository.findByShoesIdInWithPessimisticLock(idList);
        return shoesEntityList.stream()
            .map(ShoesEntity::toDomain).toList();
    }

}
