package com.msa.order;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    void test(){
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        Assertions.assertThat(result).isEqualTo(3);
    }
}
