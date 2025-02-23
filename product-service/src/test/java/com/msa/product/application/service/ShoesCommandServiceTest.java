package com.msa.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.application.port.out.ShoesSavePort;
import com.msa.product.domain.ProductFixtures;
import com.msa.product.domain.ShoesModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoesCommandServiceTest {

    @InjectMocks
    private ShoesCommandService sut;

    @Mock
    private ShoesSavePort shoesSavePort;

    @Nested
    @DisplayName("[SERVICE] 신발 정보 등록 테스트")
    class CreateShoes{

        @Test
        @DisplayName("신발 정보로 신발 모델 도메인을 생성 후 반환한다.")
        void test2000(){
            // Given
            String shoesName = "테스트 이름";
            int price = 100000;

            CreateShoesRequest request = ProductFixtures.createShoesRequest(shoesName, price);
            CreateShoesCommand command = CreateShoesCommand.from(request);

            ShoesModel shoesModel = ProductFixtures.shoesModel(1L, shoesName, price);

            when(shoesSavePort.save(any(ShoesModel.class)))
                .thenReturn(shoesModel);
            // When

            ShoesModel result = sut.createShoes(command);

            // Then
            assertThat(result).isEqualTo(shoesModel);
            assertThat(result.getShoesName()).isEqualTo(shoesModel.getShoesName());
            assertThat(result.getPrice()).isEqualTo(shoesModel.getPrice());
            assertThat(result.getShoesList().size()).isEqualTo(2);
        }

    }

}