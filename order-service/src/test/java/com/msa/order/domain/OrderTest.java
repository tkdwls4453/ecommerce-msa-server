package com.msa.order.domain;

import static org.assertj.core.api.Assertions.*;

import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.exception.InvalidCouponTypeException;
import com.msa.order.exception.InvalidTotalPriceException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Nested
    @DisplayName("주문 초기화 테스트")
    class initOrder{
        @Test
        @DisplayName("정상적인 주문 생성 정보로 주문 초기화시, 주문 접수됨 상태의 주문을 생성한다. (고정 할인 쿠폰 적용)")
        void test2000(){
            // Given
            Long customerId = 1L;
            CreateNewOrderCommand command = CreateNewOrderCommand.from(OrderFixtures.newOrderWithFixedCouponRequest());
            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When
            Order result = Order.init(customerId, command, orderTime, orderCode);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getOrderTime()).isEqualTo(orderTime);
            assertThat(result.getOrderId()).isNull();
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.ORDER_RECEIVED);
            assertThat(result.getOrderLine()).hasSize(command.orderLine().size());
            assertThat(result.getOrderCode()).isEqualTo(orderCode);
        }

        @Test
        @DisplayName("정상적인 주문 생성 정보로 주문 초기화시, 주문 접수됨 상태의 주문을 생성한다. (퍼센트 쿠폰 적용)")
        void test2001(){
            // Given
            Long customerId = 1L;
            CreateNewOrderCommand command = CreateNewOrderCommand.from(OrderFixtures.newOrderWithPercentageCouponRequest());
            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When
            Order result = Order.init(customerId, command, orderTime, orderCode);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getOrderTime()).isEqualTo(orderTime);
            assertThat(result.getOrderId()).isNull();
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.ORDER_RECEIVED);
            assertThat(result.getOrderLine()).hasSize(command.orderLine().size());
            assertThat(result.getOrderCode()).isEqualTo(orderCode);
        }

        @Test
        @DisplayName("쿠폰 정보 없이도 정상 주문 초기화시, 주문 접수됨 상태의 주문을 생성한다.")
        void test2002(){
            // Given
            Long customerId = 1L;
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .orderLine(OrderFixtures.orderLine())
                .shippingInfo(OrderFixtures.shippingInfo())
                .totalAmount(OrderFixtures.calculateTotalAmount(OrderFixtures.orderLine(), null))
                .coupon(null)
                .build();

            CreateNewOrderCommand command = CreateNewOrderCommand.from(request);

            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When
            Order result = Order.init(customerId, command, orderTime, orderCode);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getOrderTime()).isEqualTo(orderTime);
            assertThat(result.getOrderId()).isNull();
            assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.ORDER_RECEIVED);
            assertThat(result.getOrderLine()).hasSize(command.orderLine().size());
            assertThat(result.getOrderCode()).isEqualTo(orderCode);
        }

        @Test
        @DisplayName("틀린 결제 정보로 주문 초기화시, 예외를 발생시킨다.")
        void test1(){
            // Given
            Long customerId = 1L;
            int wrongTotalPrice = 3000;
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .orderLine(OrderFixtures.orderLine())
                .shippingInfo(OrderFixtures.shippingInfo())
                .totalAmount(wrongTotalPrice)
                .coupon(OrderFixtures.fixedCoupon())
                .build();

            CreateNewOrderCommand command = CreateNewOrderCommand.from(request);

            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When Then
            assertThatThrownBy(() -> {
                Order.init(customerId, command, orderTime, orderCode);
            })
                .isInstanceOf(InvalidTotalPriceException.class);
        }

        @Test
        @DisplayName("틀린 결제 정보로 주문 초기화시, 예외를 발생시킨다.")
        void test2(){
            // Given
            Long customerId = 1L;
            int wrongTotalPrice = 3000;
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .orderLine(OrderFixtures.orderLine())
                .shippingInfo(OrderFixtures.shippingInfo())
                .totalAmount(wrongTotalPrice)
                .coupon(OrderFixtures.fixedCoupon())
                .build();

            CreateNewOrderCommand command = CreateNewOrderCommand.from(request);

            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When Then
            assertThatThrownBy(() -> {
                Order.init(customerId, command, orderTime, orderCode);
            })
                .isInstanceOf(InvalidTotalPriceException.class);
        }

        @Test
        @DisplayName("유효하지 않은 쿠폰 타입으로 주문 초기화시, 예외를 발생시킨다.")
        void test3(){
            // Given
            Long customerId = 1L;
            AppliedCoupon invalidCoupon = AppliedCoupon.builder()
                .couponId(1L)
                .type("NotExits")
                .amount(3000)
                .build();

            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .orderLine(OrderFixtures.orderLine())
                .shippingInfo(OrderFixtures.shippingInfo())
                .totalAmount(OrderFixtures.calculateTotalAmount(OrderFixtures.orderLine(), invalidCoupon))
                .coupon(invalidCoupon)
                .build();

            CreateNewOrderCommand command = CreateNewOrderCommand.from(request);

            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When Then
            assertThatThrownBy(() -> {
                Order.init(customerId, command, orderTime, orderCode);
            })
                .isInstanceOf(InvalidCouponTypeException.class);
        }

        @Test
        @DisplayName("주문 아이템 없이 주문 초기화시, 예외를 발생시킨다. (쿠폰 적용 X)")
        void test4(){
            // Given
            Long customerId = 1L;
            int wrongTotalPrice = 3000;

            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .orderLine(OrderFixtures.orderLine())
                .shippingInfo(OrderFixtures.shippingInfo())
                .totalAmount(wrongTotalPrice)
                .coupon(null)
                .build();

            CreateNewOrderCommand command = CreateNewOrderCommand.from(request);

            LocalDateTime orderTime = LocalDateTime.now();
            UUID orderCode = UUID.randomUUID();

            // When Then
            assertThatThrownBy(() -> {
                Order.init(customerId, command, orderTime, orderCode);
            })
                .isInstanceOf(InvalidTotalPriceException.class);
        }


    }

}