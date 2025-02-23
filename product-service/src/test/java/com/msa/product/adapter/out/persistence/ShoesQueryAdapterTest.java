package com.msa.product.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.product.domain.ProductFixtures;
import com.msa.product.domain.Shoes;
import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.Size;
import com.msa.product.exception.NotFoundShoesException;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoesQueryAdapterTest {

    @InjectMocks
    private ShoesQueryAdapter sut;

    @Mock
    private ShoesQueryJpaRepository shoesQueryJpaRepository;

    @Nested
    @DisplayName("신발 아이디로 신발 조회")
    class FindByIdIn{
        @Test
        @DisplayName("신발 아이디 리스트로 여러 건의 신발 엔티티들을 조회하여 도메인으로 변경 후 반환한다.")
        void test2000(){
            // Given
            List<Long> idList = Arrays.asList(1L, 2L);

            when(shoesQueryJpaRepository.findAllById(idList))
                .thenReturn(ProductFixtures.shoesEntityList());

            // When
            List<Shoes> shoesList = sut.findByIdIn(idList);

            // Then
            assertThat(shoesList).hasSize(2)
                .extracting("shoesId", "size", "color", "quantity")
                .contains(
                    tuple(1L, Size.SIZE_260, Color.BLACK, new Quantity(10)),
                    tuple(2L, Size.SIZE_270, Color.WHITE, new Quantity(5))
                )
            ;
            verify(shoesQueryJpaRepository, times(1)).findAllById(idList);
        }

        @Test
        @DisplayName("찾지 못한 신발이 있을 경우 찾은 신발만 반환한다..")
        void test1(){
            // Given
            List<Long> idList = Arrays.asList(1L, 2L, 3L);

            when(shoesQueryJpaRepository.findAllById(idList))
                .thenReturn(ProductFixtures.shoesEntityList());

            // When
            List<Shoes> shoesList = sut.findByIdIn(idList);

            // Then
            assertThat(shoesList).hasSize(2)
                .extracting("shoesId", "size", "color", "quantity")
                .contains(
                    tuple(1L, Size.SIZE_260, Color.BLACK, new Quantity(10)),
                    tuple(2L, Size.SIZE_270, Color.WHITE, new Quantity(5))
                )
            ;
            verify(shoesQueryJpaRepository, times(1)).findAllById(idList);
        }

    }

}