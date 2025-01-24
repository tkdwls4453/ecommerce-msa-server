package com.msa.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class CalculatorTest {

    @Test
    void add() {
        Calculator calculator = new Calculator();
        int add = Calculator.add(1, 16);
        assertThat(add).isEqualTo(17);
    }
}