package com.msa.common.vo;

import java.util.Objects;

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

    public Money add(Money other){
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other){
        if(this.amount < other.amount){
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        return new Money(this.amount - other.amount);
    }

    public Money multiply(int multiplier){
        return new Money(this.amount * multiplier);
    }

    public Money divide(int divisor){
        if(divisor < 0) {
            throw new IllegalArgumentException("divisor must be greater than 0");
        }
        return new Money(this.amount / divisor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money money)) {
            return false;
        }
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
