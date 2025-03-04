package com.msa.product.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.adapter.in.web.dto.CreatedShoesResponse;
import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.application.port.in.CreateShoesUseCase;
import com.msa.product.domain.ShoesModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ShoesController {

    private final CreateShoesUseCase createShoesUseCase;

    @PostMapping
    ApiResponse<CreatedShoesResponse> registerShoes(
        @Valid @RequestBody CreateShoesRequest request
    ){
        ShoesModel shoes = createShoesUseCase.createShoes(CreateShoesCommand.from(request));
        return ApiResponse.success(CreatedShoesResponse.from(shoes));
    }


}
