package com.msa.payment.adapter.out;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.common.exception.FeignClientException;
import com.msa.common.response.ApiResponse;
import com.msa.payment.adapter.out.feign.OrderFeignClient;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderFeignAdapterTest {

    @InjectMocks
    private OrderFeignAdapter sut;

    @Mock
    private OrderFeignClient orderFeignClient;

    @Nested
    @DisplayName("주문 아이디로 주문 서비스에 간단 주문 정보 조회를 요청한다.")
    class FindSimpleOrderByOrderId{
        @Test
        @DisplayName("간단 주문 정보 조회에 성공하면 간단 주문 정보를 반환한다.")
        void test2000(){
            // Given
            Long orderId = 1L;
            LocalDateTime orderTime = LocalDateTime.now();
            SimpleOrderResponse simpleOrderResponse = SimpleOrderResponse.builder()
                .customerId(1L)
                .orderId(orderId)
                .orderCode("test-order-code")
                .orderStatus("PAYMENT_PENDING")
                .totalPrice(50000)
                .orderTime(orderTime.toString())
                .build();

            when(orderFeignClient.getSimpleOrderById(orderId))
                .thenReturn(ApiResponse.success(simpleOrderResponse));

            // When
            SimpleOrderResponse result = sut.findSimpleOrderByOrderId(orderId);

            // Then
            assertThat(result).isEqualTo(simpleOrderResponse);
        }

        @Test
        @DisplayName("간단 주문 정보 조회에 실패하면 예외를 반환한다.")
        void test1(){
            // Given
            Long orderId = 1L;
            LocalDateTime orderTime = LocalDateTime.now();

            when(orderFeignClient.getSimpleOrderById(orderId))
                .thenThrow(RuntimeException.class);

            // When Then
            assertThatThrownBy(() -> sut.findSimpleOrderByOrderId(orderId))
                .isInstanceOf(FeignClientException.class);

            verify(orderFeignClient, times(1)).getSimpleOrderById(orderId);
        }
    }

    @Nested
    @DisplayName("주문 서비스에 주문 상태 변경을 요청한다.")
    class ChangeToPreparing{
        @Test
        @DisplayName("주문 아이디로 주문 서비스에 주문 상태 변경을 요청한다.")
        void test2000(){
            // Given
            Long orderId = 1L;

            // When
            sut.changeToPreparing(orderId);

            // Then
            verify(orderFeignClient, times(1)).prepareOrder(orderId);
        }
    }
}