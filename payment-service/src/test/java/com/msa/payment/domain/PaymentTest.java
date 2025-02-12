package com.msa.payment.domain;

import static org.assertj.core.api.Assertions.*;

import com.msa.common.vo.Money;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.exception.PaymentOrderInvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @DisplayName("결제 정보 초기화 테스트")
    @Nested
    class initPayment{

        @Test
        @DisplayName("정상적인 정보로 결제 생성시, 초기화된 결제를 반환한다.")
        void test2000(){
            // Given
            Long customerId = 1L;
            CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(1L)
                .amount(new Money(30000))
                .build();
            CreatePaymentCommand command = CreatePaymentCommand.from(request);

            // When
            Payment result = Payment.init(customerId, command);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getOrderId()).isEqualTo(1L);
            assertThat(result.getAmount()).isEqualTo(new Money(30000));
            assertThat(result.getPaymentStatus()).isEqualTo(PaymentStatus.INITIALIZED);
        }

//        @Test
//        @DisplayName("결제 불가는한 주문으로 결제 생성시, 예외를 반환한다.")
//        void test1(){
//            // Given
//            Long customerId = 1L;
//            CreatePaymentRequest request = CreatePaymentRequest.builder()
//                .orderId(1L)
//                .amount(new Money(30000))
//                .build();
//            CreatePaymentCommand command = CreatePaymentCommand.from(request);
//            SimpleOrderResponse invalidSimpleOrderResponse = OrderFixtures.invalidSimpleOrderResponse();
//
//            // When Then
//            assertThatThrownBy(() -> {
//                Payment.init(customerId, command);
//            })
//                .isInstanceOf(PaymentOrderInvalidException.class);
//        }

//        @Test
//        @DisplayName("주문하지 않은 유저가 결제 생성시, 예외를 반환한다.")
//        void test2(){
//            // Given
//            Long invalidCustomerId = 2L;
//            CreatePaymentRequest request = CreatePaymentRequest.builder()
//                .orderId(1L)
//                .amount(new Money(30000))
//                .build();
//            CreatePaymentCommand command = CreatePaymentCommand.from(request);
//            SimpleOrderResponse simpleOrderResponse = OrderFixtures.simpleOrderResponse();
//
//            // When Then
//            assertThatThrownBy(() -> {
//                Payment.init(invalidCustomerId, command);
//            })
//                .isInstanceOf(OrderPermissionDeniedException.class);
//        }
//
//        @Test
//        @DisplayName("주문 금액과 결제 금액이 일치하지 않은 정보로 결제 생성시, 예외를 반환한다.")
//        void test3(){
//            // Given
//            Long customerId = 1L;
//            CreatePaymentRequest request = CreatePaymentRequest.builder()
//                .orderId(1L)
//                .amount(new Money(300))
//                .build();
//            CreatePaymentCommand command = CreatePaymentCommand.from(request);
//            SimpleOrderResponse simpleOrderResponse = OrderFixtures.simpleOrderResponse();
//
//            // When Then
//            assertThatThrownBy(() -> {
//                Payment.init(customerId, command);
//            })
//                .isInstanceOf(PriceMismatchException.class);
//        }
    }
}