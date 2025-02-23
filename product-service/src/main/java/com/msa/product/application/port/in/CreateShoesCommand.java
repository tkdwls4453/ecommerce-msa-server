package com.msa.product.application.port.in;

import com.msa.common.vo.Money;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.adapter.in.web.dto.ShoesDto;
import com.msa.product.domain.Shoes;
import com.msa.product.domain.vo.ShoesName;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateShoesCommand(
    ShoesName shoesName,
    Money price,
    List<Shoes> shoesList
) {

    public static CreateShoesCommand from(CreateShoesRequest request) {
        return CreateShoesCommand.builder()
            .shoesName(new ShoesName(request.shoesName()))
            .price(new Money(request.price()))
            .shoesList(request.shoesList().stream()
                .map(ShoesDto::toDomain)
                .toList()
            )
            .build();
    }
}
