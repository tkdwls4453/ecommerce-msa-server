package com.msa.product.application.service;

import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.application.port.in.CreateShoesUseCase;
import com.msa.product.domain.ShoesModel;
import org.springframework.stereotype.Service;

@Service
public class ShoesCommandService implements CreateShoesUseCase {

    @Override
    public ShoesModel createShoes(CreateShoesCommand command) {
        return null;
    }
}
