package com.msa.order.domain.vo;

public record Money(
    int amount
) {

    public Money(int amount) {
        if(amount < 0) {
            // TODO: CustomException 사용을 고려
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        this.amount = amount;
    }
}
