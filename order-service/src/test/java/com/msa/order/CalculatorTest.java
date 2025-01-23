package com.msa.order;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    Calculator sut = new Calculator();

    @Test
    void addTest(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }
}