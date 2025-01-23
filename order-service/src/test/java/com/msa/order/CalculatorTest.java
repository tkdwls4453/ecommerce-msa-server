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

    @Test
    void addTest2(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add2(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest3(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add3(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest4(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add4(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest5(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add5(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest6(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add6(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest7(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add7(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest8(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add8(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest9(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add9(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest10(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add10(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest11(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add11(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }

    @Test
    void addTest12(){
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = sut.add11(a, b);

        // Then
        assertThat(result).isEqualTo(a + b);
    }


}