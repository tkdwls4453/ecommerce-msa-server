package com.msa.product.application.port.in;

import com.msa.product.domain.ShoesModel;

public interface CreateShoesUseCase {

    ShoesModel createShoes(CreateShoesCommand command);
}
