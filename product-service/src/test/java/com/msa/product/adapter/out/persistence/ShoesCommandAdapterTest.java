package com.msa.product.adapter.out.persistence;



import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.msa.product.domain.ProductFixtures;
import com.msa.product.domain.Shoes;
import com.msa.product.domain.ShoesModel;
import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.Size;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoesCommandAdapterTest {

    @InjectMocks
    private ShoesCommandAdapter sut;

    @Mock
    private ShoesModelCommandJpaRepository shoesModelCommandJpaRepository;

    @Mock
    private ShoesCommandJpaRepository shoesCommandJpaRepository;

    @Nested
    @DisplayName("신발 정보 저장 테스트")
    class saveShoesModel{
        @Test
        @DisplayName("신발 정보 도메인을 엔티티로 변경하여 저장 후 다시 도메인으로 변환하여 반환한다.")
        void test2000(){
            // Given
            ShoesModel shoesModel = ProductFixtures.shoesModel(null, "테스트 신발", 100000);
            ShoesModelEntity shoesModelEntity = ProductFixtures.shoesModelEntity(1L, "테스트 신발", 100000);

            when(shoesModelCommandJpaRepository.save(any())).thenReturn(shoesModelEntity);

            // When
            ShoesModel savedShoesModel = sut.save(shoesModel);
            List<Shoes> shoesList = savedShoesModel.getShoesList();

            // Then
            assertThat(savedShoesModel).isNotNull();
            assertThat(savedShoesModel.getModelId()).isEqualTo(1L);
            assertThat(savedShoesModel.getShoesName()).isEqualTo(shoesModel.getShoesName());
            assertThat(savedShoesModel.getPrice()).isEqualTo(shoesModel.getPrice());

            assertThat(shoesList).hasSize(2);
        }
    }

    @Nested
    @DisplayName("여러 신발 정보 저장 테스트")
    class SaveAll{
        @Test
        @DisplayName("여러 건의 신발 정보를 저장 후 도메인으로 변경하여 반환한다.")
        void test2000(){
            // Given
            List<Shoes> shoesList = ProductFixtures.shoesList();
            List<ShoesEntity> shoesEntityList = ProductFixtures.shoesEntityList();

            when(shoesCommandJpaRepository.saveAll(anyList())).thenReturn(shoesEntityList);

            // When
            List<Shoes> result = sut.saveAll(shoesList);

            // Then
            assertThat(result).hasSize(2)
                .extracting("shoesId", "size", "color", "quantity")
                .contains(
                    tuple(1L, Size.SIZE_260, Color.BLACK, new Quantity(10)),
                    tuple(2L, Size.SIZE_270, Color.WHITE, new Quantity(5))
                );
        }
    }
}