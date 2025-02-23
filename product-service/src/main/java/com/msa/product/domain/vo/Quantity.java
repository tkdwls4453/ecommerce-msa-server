package com.msa.product.domain.vo;

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

    public boolean isLessThen(Integer amount) {
        return this.quantity < amount;
    }
}
