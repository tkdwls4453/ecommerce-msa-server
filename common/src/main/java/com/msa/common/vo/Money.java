package com.msa.common.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(
    BigDecimal amount
) {

    public Money(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        this.amount = amount.stripTrailingZeros();
    }

    public Money(int amount) {
        this(new BigDecimal(amount));
    }

    public Money add(Money other){
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other){
        if(this.amount.compareTo(other.amount) < 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(int multiplier){
        return new Money(this.amount.multiply(new BigDecimal(multiplier)));
    }

    public Money divide(int divisor){
        if(divisor < 0) {
            throw new IllegalArgumentException("divisor must be greater than 0");
        }
        return new Money(this.amount.divide(new BigDecimal(divisor), 0, RoundingMode.HALF_UP));
    }

    public Money getPercentDiscountAmount(int percent){
        if(percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        BigDecimal discountRate = BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = this.amount.multiply(discountRate);
        return new Money(discountAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money money)) {
            return false;
        }
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
