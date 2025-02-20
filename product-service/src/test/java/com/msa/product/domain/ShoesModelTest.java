package com.msa.product.domain;

import static org.assertj.core.api.Assertions.*;

import com.msa.common.vo.Money;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.domain.vo.ShoesName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ShoesModelTest {

    @Nested
    @DisplayName("신발 생성 테스트")
    class GenerateShoes{
        @Test
        @DisplayName("신발 등록 커맨드로 신발 모델 도메인을 생성한다.")
        void test2000(){
            // Given
            String shoesName = "테스트 이름";
            int price = 100000;

            CreateShoesRequest request = ProductFixtures.createShoesRequest(shoesName, price);
            CreateShoesCommand command = CreateShoesCommand.from(request);

            // When
            ShoesModel shoesModel = ShoesModel.generate(command);

            // Then
            assertThat(shoesModel).isNotNull();
            assertThat(shoesModel.getShoesName()).isEqualTo(new ShoesName(shoesName));
            assertThat(shoesModel.getPrice()).isEqualTo(new Money(price));
            assertThat(shoesModel.getShoesList()).hasSize(2);
        }
    }
}