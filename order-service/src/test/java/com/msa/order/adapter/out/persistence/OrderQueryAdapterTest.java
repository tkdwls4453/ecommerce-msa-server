package com.msa.order.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
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
class OrderQueryAdapterTest {

    @InjectMocks
    private OrderQueryAdapter sut;

    @Mock
    private OrderQueryJpaRepository orderQueryJpaRepository;

    @Nested
    @DisplayName("주문 조회 테스트")
    class FindById{
        @Test
        @DisplayName("주문 아이디로 조회시, 주문 엔티티를 조회후 주문 도메인으로 변경하여 반환한다.")
        void test1(){
            // Given
            Long orderId = 1L;
            LocalDateTime orderTime = LocalDateTime.now();
            Order order = OrderFixtures.order(orderId, OrderStatus.PAYMENT_PENDING, orderTime);
            OrderEntity orderEntity = OrderEntity.from(order);

            when(orderQueryJpaRepository.findById(orderId))
                .thenReturn(Optional.of(orderEntity));

            // When
            Optional<Order> result = sut.findById(orderId);

            // Then
            assertThat(result.isPresent()).isTrue();
            assertThat(result.get().getOrderId()).isEqualTo(orderId);
            assertThat(result.get().getOrderStatus()).isEqualTo(OrderStatus.PAYMENT_PENDING);
            assertThat(result.get().getTotalPrice()).isEqualTo(order.getTotalPrice());
            assertThat(result.get().getOrderTime()).isEqualTo(orderTime);

            verify(orderQueryJpaRepository, times(1)).findById(orderId);

        }

        @Test
        @DisplayName("존재하지 않은 아이디로 조회시 빈 Optioal 을 반환한다.")
        void test2000(){
            // Given
            Long nonExistsOrderId = 100L;
            LocalDateTime orderTime = LocalDateTime.now();

            when(orderQueryJpaRepository.findById(nonExistsOrderId))
                .thenReturn(Optional.empty());

            // When
            Optional<Order> result = sut.findById(nonExistsOrderId);

            // Then
            assertThat(result.isEmpty()).isTrue();
        }
    }
}