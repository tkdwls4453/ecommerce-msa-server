package com.msa.product.adapter.in.web.dto;

import com.msa.product.domain.vo.*;

public record ShoeCreateRequest(
        Long modelId,
        Price price,
        Long shoesId,
        ShoesName shoesName,
        Size size,
        Color color,
        Quantity quantity
) {
}
