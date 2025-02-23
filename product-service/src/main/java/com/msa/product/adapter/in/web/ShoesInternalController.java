package com.msa.product.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.product.adapter.in.web.dto.DecreaseStockRequest;
import com.msa.product.adapter.in.web.dto.RollbackStockRequest;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.ProductStockUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/products")
public class ShoesInternalController {
    private final ProductStockUseCase productStockUseCase;

    @PostMapping("/stock/decrease")
    ApiResponse<Void> decreaseStock(
        @Valid @RequestBody DecreaseStockRequest request
    ){
        productStockUseCase.decreaseStock(DecreaseStockCommand.from(request));
        return ApiResponse.success();
    }
}
