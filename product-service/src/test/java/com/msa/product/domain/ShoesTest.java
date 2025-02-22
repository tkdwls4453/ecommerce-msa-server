package com.msa.product.domain;

import static org.assertj.core.api.Assertions.*;

import com.msa.product.domain.vo.Quantity;
import com.msa.product.exception.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ShoesTest {

    @Nested
    @DisplayName("상품 재고 감소 테스트")
    class DecreaseQuantity{
        @Test
        @DisplayName("상품의 재고가 정상적으로 감소된다.")
        void test2000(){
            // Given
            Shoes shoes = Shoes.builder()
                .shoesId(1L)
                .quantity(new Quantity(10))
                .build();

            Integer amount = 5;

            // When
            shoes.decreaseQuantity(amount);

            // Then
            assertThat(shoes.getQuantity()).isEqualTo(new Quantity(5));
        }

        @Test
        @DisplayName("상품의 재고가 부족한 경우 예외를 반환한다.")
        void test1(){
            // Given
            Shoes shoes = Shoes.builder()
                .shoesId(1L)
                .quantity(new Quantity(1))
                .build();

            Integer amount = 5;

            // When Then
            assertThatThrownBy(() -> shoes.decreaseQuantity(amount))
                .isInstanceOf(InsufficientStockException.class);
        }
    }
}