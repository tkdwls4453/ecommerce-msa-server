package com.msa.product.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Price(
        long price
) {

    public Price {
        validate(price);
    }

    private void validate(long value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }
}
