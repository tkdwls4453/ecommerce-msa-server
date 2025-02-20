package com.msa.product.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.application.port.in.CreateShoesCommand;
import com.msa.product.application.port.in.CreateShoesUseCase;
import com.msa.product.domain.ProductFixtures;
import com.msa.product.domain.ShoesModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShoesController.class)
class ShoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateShoesUseCase createShoesUseCase;

    @Nested
    @DisplayName("POST /products")
    class registerProduct{
        @Test
        @DisplayName("상품 정보로 등록에 성공하면 200 OK 를 반환한다.")
        void test2000() throws Exception {
            // Given
            String modelName = "테스트 신발";
            int price = 100000;
            CreateShoesRequest request = ProductFixtures.createShoesRequest(modelName, price);
            String body = objectMapper.writeValueAsString(request);

            ShoesModel shoesModel = ProductFixtures.shoesMode(1L,modelName, price);
            when(createShoesUseCase.createShoes(any(CreateShoesCommand.class)))
                .thenReturn(shoesModel);

            // When Then
            mockMvc.perform(post("/products")
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"))
                .andExpect(jsonPath("$.data.modelId").value(1L))
                .andExpect(jsonPath("$.data.shoesName").value(modelName))
                .andExpect(jsonPath("$.data..price").value(price));

            verify(createShoesUseCase, times(1)).createShoes(any(CreateShoesCommand.class));
        }

        @Test
        @DisplayName("모델의 이름 없이 등록시 400 BAD REQUEST 를 반환한다.")
        void test1() throws Exception {
            // Given
            int price = 100000;
            CreateShoesRequest request = ProductFixtures.createShoesRequest(null, price);
            String body = objectMapper.writeValueAsString(request);


            // When Then
            mockMvc.perform(post("/products")
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(createShoesUseCase, times(0)).createShoes(any(CreateShoesCommand.class));
        }

        @Test
        @DisplayName("모델의 가격 없이 등록시 400 BAD REQUEST 를 반환한다.")
        void test2() throws Exception {
            // Given
            String modelName = "테스트 신발";
            CreateShoesRequest request = ProductFixtures.createShoesRequest(modelName, null);
            String body = objectMapper.writeValueAsString(request);


            // When Then
            mockMvc.perform(post("/products")
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(createShoesUseCase, times(0)).createShoes(any(CreateShoesCommand.class));
        }
    }
}