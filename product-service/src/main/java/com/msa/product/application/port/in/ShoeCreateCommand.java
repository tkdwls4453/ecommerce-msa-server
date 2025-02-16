package com.msa.product.application.port.in;

import com.msa.product.domain.vo.*;

public record ShoeCreateCommand(
        Long modelId,
        Price price,
        Long shoesId,
        ShoesName shoesName,
        Size size,
        Color color,
        Quantity quantity
) {
}
