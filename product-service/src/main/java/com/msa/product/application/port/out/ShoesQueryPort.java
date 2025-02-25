package com.msa.product.application.port.out;

import com.msa.product.domain.Shoes;
import java.util.List;
import java.util.Optional;

public interface ShoesQueryPort {

    List<Shoes> findByIdIn(List<Long> idList);

    List<Shoes> findByShoesIdInWithPessimisticLock(List<Long> idList);

    Optional<Shoes> findById(Long shoesId);
}
