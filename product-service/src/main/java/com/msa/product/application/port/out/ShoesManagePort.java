package com.msa.product.application.port.out;

import com.msa.product.domain.Shoes;
import java.util.List;

public interface ShoesManagePort {

    void decreaseStock(Long shoesId, Integer quantity);

    void saveAll(List<Shoes> shoesList);
}
