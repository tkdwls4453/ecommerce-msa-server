package com.msa.product.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Quantity(
        int quantity
) {
    public Quantity {
        validate(quantity);
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }
}
