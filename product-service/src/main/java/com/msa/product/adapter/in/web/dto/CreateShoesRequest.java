package com.msa.product.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateShoesRequest (
    @NotBlank(message = "모델의 이름은 필수입니다.")
    String shoesName,

    @NotNull(message = "모델의 이름을 필수입니다.")
    Integer price,

    List<ShoesDto> shoesList
){

}
