package com.msa.order.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.order.application.port.out.OrderQueryPort;
import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
import com.msa.order.exception.NotFoundOrderException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    @InjectMocks
    private OrderQueryService sut;

    @Mock
    private OrderQueryPort orderQueryPort;
    @Nested
    @DisplayName("주문 아이디로 주문을 조회한다.")
    class GetOrderById {

        @Test
        @DisplayName("주문 아이디로 주문 조회시, 주문 도메인을 반환한다.")
        void test2000(){
            // Given
            Long orderId = 1L;
            LocalDateTime orderTime = LocalDateTime.now();
            Order order = OrderFixtures.order(orderId, OrderStatus.PAYMENT_PENDING, orderTime);
            when(orderQueryPort.findById(orderId))
                .thenReturn(Optional.of(order));

            // When
            Order result = sut.getOrderById(orderId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getOrderId()).isEqualTo(orderId);
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PAYMENT_PENDING);
            assertThat(result.getOrderTime()).isEqualTo(orderTime);

            verify(orderQueryPort, times(1)).findById(orderId);
        }

        @Test
        @DisplayName("존재하지 않는 주문 아이디로 주문 조회시, 예외를 반환한다.")
        void test1(){
            // Given
            Long nonExistsOrderId = 100L;

            when(orderQueryPort.findById(nonExistsOrderId)).thenReturn(Optional.empty());

            // When Then
            assertThatThrownBy(
                () -> sut.getOrderById(nonExistsOrderId)
            )
                .isInstanceOf(NotFoundOrderException.class);

        }

        @Test
        @DisplayName("주문 아이디가 null 인 경우, 예외를 반환한다.")
        void test2(){
            // Given
            Long orderId = null;

            // When Then
            assertThatThrownBy(
                () -> sut.getOrderById(orderId)
            )
                .isInstanceOf(NotFoundOrderException.class);
        }
    }
}