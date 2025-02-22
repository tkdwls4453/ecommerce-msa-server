package com.msa.product.adapter.out.persistence;

import com.msa.product.application.port.out.ShoesQueryPort;
import com.msa.product.domain.Shoes;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ShoesQueryAdapter implements ShoesQueryPort {

    @Override
    public List<Shoes> findByIdIn(List<Long> idList) {
        return List.of();
    }
}
