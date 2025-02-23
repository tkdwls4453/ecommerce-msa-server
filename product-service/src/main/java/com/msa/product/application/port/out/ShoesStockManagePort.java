package com.msa.product.application.port.out;

import com.msa.product.domain.Shoes;
import java.util.List;

public interface ShoesStockManagePort {

    void updateStock(List<Shoes> shoesList);
}
