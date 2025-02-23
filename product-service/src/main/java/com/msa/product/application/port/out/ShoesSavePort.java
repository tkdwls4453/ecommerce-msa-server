package com.msa.product.application.port.out;

import com.msa.product.domain.ShoesModel;

public interface ShoesSavePort {

    ShoesModel save(ShoesModel shoesModel);

}
