package com.msa.order.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.application.port.out.OrderCreatePort;
import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
import com.msa.common.vo.Money;
import com.msa.order.exception.InsufficientStockException;
import com.msa.order.exception.InvalidCouponException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService sut;

    @Mock
    private DecreaseStockUseCase decreaseStockUseCase;

    @Mock
    private ApplyCouponUseCase applyCouponUseCase;

    @Mock
    private OrderCreatePort orderCreatePort;

    /**
     * 주문 접수 서비스 기능
     * 입력: userId, 주문 접수 커맨드
     * 주문 초기화 (생성), 결제 금액 검증 (도메인)
     * 쿠폰 적용 -> 쿠폰 서비스
     * 재고 차감 -> 상품 섭비스
     * 데이터베이스에 주문 저장
     */

    @Nested
    @DisplayName("고객 아이디와 주문 접수 정보로 주문을 접수한다.")
    class newOrder{
        @Test
        @DisplayName("주문 접수시, 쿠폰 적용과 재고를 차감하고 주문을 성공적으로 생성한다.")
        void test2000(){
            // Given
            Long customerId = 1L;
            CreateNewOrderCommand command = CreateNewOrderCommand.from(OrderFixtures.newOrderWithFixedCouponRequest());
            LocalDateTime orderTime = LocalDateTime.now();
            Order savedOrder = OrderFixtures.order(1L, OrderStatus.PAYMENT_PENDING, orderTime);

            when(orderCreatePort.save(any(Order.class))).thenReturn(savedOrder);

            // When
            Order result = sut.createNewOrder(1L, command);

            // Then
            verify(decreaseStockUseCase, times(1)).decreaseStock(command.orderLine());
            verify(applyCouponUseCase, times(1)).applyCoupon(any(Money.class), any(Money.class), any(Long.class));
            verify(orderCreatePort, times(1)).save(any(Order.class));

            assertThat(result).isNotNull();
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getOrderLine()).hasSize(command.orderLine().size());
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PAYMENT_PENDING);
        }

        @Test
        @DisplayName("주문 접수시, 재고 감소에 실패하면 실패 주문을 생성하고 예외를 발생시킨다.")
        void test1(){
            // Given
            Long customerId = 1L;
            CreateNewOrderCommand command = CreateNewOrderCommand.from(OrderFixtures.newOrderWithFixedCouponRequest());

            doThrow(InsufficientStockException.class)
                .when(decreaseStockUseCase)
                .decreaseStock(ArgumentMatchers.anyList());

            // When Then
            assertThatThrownBy(() -> sut.createNewOrder(customerId, command))
                .isInstanceOf(InsufficientStockException.class);

            verify(decreaseStockUseCase, times(1)).decreaseStock(command.orderLine());
            verify(applyCouponUseCase, times(0)).applyCoupon(any(Money.class), any(Money.class), any(Long.class));
            verify(orderCreatePort, times(1)).save(any(Order.class));
        }

        @Test
        @DisplayName("주문 접수시, 쿠폰 적용에 실패하면 감소된 재고를 복구하고 실패 주문을 생성하고 예외를 발생시킨다.")
        void test2(){
            // Given
            Long customerId = 1L;
            CreateNewOrderCommand command = CreateNewOrderCommand.from(OrderFixtures.newOrderWithFixedCouponRequest());

            doThrow(InvalidCouponException.class)
                .when(applyCouponUseCase)
                .applyCoupon(any(Money.class), any(Money.class), any(Long.class));

            // When Then
            assertThatThrownBy(() -> sut.createNewOrder(customerId, command))
                .isInstanceOf(InvalidCouponException.class);

            verify(decreaseStockUseCase, times(1)).decreaseStock(command.orderLine());
            verify(applyCouponUseCase, times(1)).applyCoupon(any(Money.class), any(Money.class), any(Long.class));
            verify(decreaseStockUseCase, times(1)).rollback(command.orderLine());
            verify(orderCreatePort, times(1)).save(any(Order.class));
        }

    }

}