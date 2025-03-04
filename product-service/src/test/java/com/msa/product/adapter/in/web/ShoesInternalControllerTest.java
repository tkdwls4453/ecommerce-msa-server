package com.msa.product.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.product.adapter.in.web.dto.DecreaseStockRequest;
import com.msa.product.adapter.in.web.dto.RollbackStockRequest;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.port.in.ProductRedisStockUseCase;
import com.msa.product.application.port.in.ProductStockUseCase;
import com.msa.product.application.port.in.RollbackStockCommand;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShoesInternalController.class)
class ShoesInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductRedisStockUseCase productStockUseCase;

    @Nested
    @DisplayName("POST /internal/products/stock/decrease")
    class DecreaseStock {
        @Test
        @DisplayName("주문 상품 정보로 상품 수량 감소에 성공하면 200 OK 를 반환한다.")
        void test2000() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            DecreaseStockRequest request = DecreaseStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/decrease")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"));

            verify(productStockUseCase, times(1)).decreaseStock(any(DecreaseStockCommand.class));
        }

        @Test
        @DisplayName("주문 상품 아이디 없이 수량 감소 요청을 하면 400 BAD_REQUEST 를 반환한다.")
        void test1() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(null)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            DecreaseStockRequest request = DecreaseStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/decrease")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(productStockUseCase, times(0)).decreaseStock(any(DecreaseStockCommand.class));
        }

        @Test
        @DisplayName("주문 수량 없이 수량 감소 요청을 하면 400 BAD_REQUEST 를 반환한다.")
        void test2() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(null)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            DecreaseStockRequest request = DecreaseStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/decrease")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(productStockUseCase, times(0)).decreaseStock(any(DecreaseStockCommand.class));
        }
    }

    @Nested
    @DisplayName("POST /internal/products/stock/rollback")
    class Rollback {
        @Test
        @DisplayName("주문 상품 정보로 상품의 재고를 복구한다.")
        void test2000() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            RollbackStockRequest request = RollbackStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/rollback")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"));

            verify(productStockUseCase, times(1)).rollbackStock(any(RollbackStockCommand.class));
        }

        @Test
        @DisplayName("주문 상품 아이디 없이 재고 복구 요청시 400 BAD_REQUEST 를 반환한다.")
        void test1() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(null)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            RollbackStockRequest request = RollbackStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/rollback")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(productStockUseCase, times(0)).rollbackStock(any(RollbackStockCommand.class));
        }

        @Test
        @DisplayName("주문 상품 수량 없이 재고 복구 요청시 400 BAD_REQUEST 를 반환한다.")
        void test2() throws Exception {
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(null)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            RollbackStockRequest request = RollbackStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then

            mockMvc.perform(post("/internal/products/stock/rollback")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));

            verify(productStockUseCase, times(0)).rollbackStock(any(RollbackStockCommand.class));
        }
    }
}