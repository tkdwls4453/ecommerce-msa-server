package com.msa.product.application.service;

import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.application.port.in.CreateShoesUseCase;
import com.msa.product.application.port.out.ShoesSavePort;
import com.msa.product.domain.ShoesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoesCommandService implements CreateShoesUseCase {
    private final ShoesSavePort shoesSavePort;

    @Override
    public ShoesModel createShoes(CreateShoesCommand command) {

        ShoesModel shoesModel = ShoesModel.generate(command);
        return shoesSavePort.save(shoesModel);
    }
}
