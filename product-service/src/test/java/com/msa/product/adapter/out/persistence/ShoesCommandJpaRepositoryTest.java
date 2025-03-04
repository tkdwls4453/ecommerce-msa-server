package com.msa.product.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class ShoesCommandJpaRepositoryTest {

    @Autowired
    private ShoesCommandJpaRepository shoesCommandJpaRepository;

    @AfterEach
    void tearDown() {
        shoesCommandJpaRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("신발 재고 업데이트 테스트")
    class UpdateStock{

        ShoesEntity shoesEntity1 = ShoesEntity.builder()
            .size(Size.fromInt(260))
            .color(Color.BLACK)
            .quantity(10)
            .build();

        ShoesEntity shoesEntity2 = ShoesEntity.builder()
            .size(Size.fromInt(270))
            .color(Color.WHITE)
            .quantity(5)
            .build();

        @Test
        @DisplayName("신발 아이디와 재고 정보로 신발의 재고를 업데이트한다.")
        void test2000(){
            // Given
            List<ShoesEntity> shoesEntityList = Arrays.asList(shoesEntity1, shoesEntity2);
            shoesCommandJpaRepository.saveAllAndFlush(shoesEntityList);


            // When
            shoesCommandJpaRepository.updateStock(1L, 3);

            // Then
            Optional<ShoesEntity> result = shoesCommandJpaRepository.findById(1L);
            assertThat(result.isPresent()).isTrue();
            assertThat(result.get().getQuantity()).isEqualTo(3);
        }
    }
}