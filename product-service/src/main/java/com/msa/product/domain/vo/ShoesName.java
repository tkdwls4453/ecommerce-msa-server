package com.msa.product.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ShoesName(
        String shoesName
) {
}
