package com.msa.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void calculate_test() {
        //given

        //when
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);

        //then
        assertEquals(3, result);
    }

}