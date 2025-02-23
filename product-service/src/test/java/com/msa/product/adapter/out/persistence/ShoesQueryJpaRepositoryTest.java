package com.msa.product.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import com.msa.product.domain.ProductFixtures;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ShoesQueryJpaRepositoryTest {

    @Autowired
    private ShoesQueryJpaRepository sut;

    @Autowired
    private ShoesCommandJpaRepository shoesCommandJpaRepository;

    @AfterEach
    void tearDown() {
        shoesCommandJpaRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("신발 여러 건 조회")
    class FindByShoesIdInWithPessimisticLock{

        @Test
        @DisplayName("신발 아이디 리스트로 여러 건의 신발들을 조회한다")
        void test2000(){
            // Given
            List<ShoesEntity> shoesEntityList = ProductFixtures.nonSavedShoesEntityList();

            List<ShoesEntity> savedShoesEntityList = shoesCommandJpaRepository.saveAllAndFlush(
                shoesEntityList);

            List<Long> idList = savedShoesEntityList.stream()
                .map(ShoesEntity::getShoesId).toList();

            // When
            List<ShoesEntity> result = sut.findByShoesIdInWithPessimisticLock(idList);

            // Then
            assertThat(result).extracting("shoesId", "quantity")
                .containsExactly(
                    tuple(idList.get(0), 10),
                    tuple(idList.get(1), 5)
                );
        }
    }
}