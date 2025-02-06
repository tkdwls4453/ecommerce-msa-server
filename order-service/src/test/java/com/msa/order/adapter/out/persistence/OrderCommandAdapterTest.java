package com.msa.order.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderCommandAdapterTest {

    @InjectMocks
    OrderCommandAdapter sut;

    @Mock
    OrderCommandJpaRepository orderCommandJpaRepository;

    @Test
    @DisplayName("Order 도메인 객체를 저장하면 OrderEntity로 변환되어 저장되고, 저장된 엔티티가 다시 도메인 객체로 변환되어 반환된다.")
    void test2000() {
        // Given
        LocalDateTime orderTime = LocalDateTime.now();
        Order order = OrderFixtures.order(1L, OrderStatus.PAYMENT_PENDING, orderTime);
        OrderEntity orderEntity = OrderEntity.from(order);

        when(orderCommandJpaRepository.save(any(OrderEntity.class)))
            .thenReturn(orderEntity);

        // When
        Order result = sut.save(order);

        // Then
        verify(orderCommandJpaRepository, times(1)).save(any(OrderEntity.class));
        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(result.getOrderStatus()).isEqualTo(order.getOrderStatus());
        assertThat(result.getOrderTime()).isEqualTo(order.getOrderTime());
        assertThat(result.getOrderCode()).isEqualTo(order.getOrderCode());
        assertThat(result.getOrderLine().size()).isEqualTo(order.getOrderLine().size());
    }
}