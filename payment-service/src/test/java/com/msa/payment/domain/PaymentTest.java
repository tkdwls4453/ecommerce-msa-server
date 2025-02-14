package com.msa.payment.domain;

import static org.assertj.core.api.Assertions.*;

import com.msa.common.vo.Money;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.exception.OrderPermissionDeniedException;
import com.msa.payment.exception.PaymentOrderInvalidException;
import com.msa.payment.exception.PriceMismatchException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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
    }

    @DisplayName("결제 정보 검증, 상태 변경 테스트")
    @Nested
    class verifyAndPending{

        @Test
        @DisplayName("결제 정보를 검증하고 정상적인 경우 PENDING 상태로 변경한다.")
        void test2000(){
            // Given
            Long customerId = 1L;
            String paymentKey = "test_payment_key";

            Payment payment = PaymentFixtures.initedPayment();
            SimpleOrderResponse order = OrderFixtures.simpleOrderResponse();

            // When
            payment.verifyAndPending(customerId, order, paymentKey);

            // Then
            assertThat(payment.getOrderCode()).isEqualTo("test_order_code");
            assertThat(payment.getPaymentKey()).isEqualTo(paymentKey);
            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
        }

        @Test
        @DisplayName("결제 불가는한 주문으로 결제 생성시, 예외를 반환한다.")
        void test1(){
            // Given
            Long customerId = 1L;
            String paymentKey = "test_payment_key";
            Payment payment = PaymentFixtures.initedPayment();
            SimpleOrderResponse invalidOrder = OrderFixtures.invalidSimpleOrderResponse();

            // When Then
            assertThatThrownBy(() -> {
                payment.verifyAndPending(customerId, invalidOrder, paymentKey);
            })
                .isInstanceOf(PaymentOrderInvalidException.class);

            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.INITIALIZED);
        }

        @Test
        @DisplayName("주문하지 않은 유저가 결제 검증시, 예외를 반환한다.")
        void test2(){
            // Given
            Long invalidCustomerId = 2L;
            String paymentKey = "test_payment_key";
            Payment payment = PaymentFixtures.initedPayment();
            SimpleOrderResponse order = OrderFixtures.simpleOrderResponse();

            // When Then
            assertThatThrownBy(() -> {
                payment.verifyAndPending(invalidCustomerId, order, paymentKey);
            })
                .isInstanceOf(OrderPermissionDeniedException.class);

            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.INITIALIZED);
        }

        @Test
        @DisplayName("주문 금액과 결제 금액이 일치하지 않은 경우, 예외를 반환한다.")
        void test3(){
            // Given
            Long customerId = 1L;
            String paymentKey = "test_payment_key";
            Payment payment = PaymentFixtures.initedPayment();
            SimpleOrderResponse invalidAmountOrder = OrderFixtures.simpleOrderResponse(
                1L,
                1L,
                new Money(1000)
            );

            // When Then
            assertThatThrownBy(() -> {
                payment.verifyAndPending(customerId, invalidAmountOrder, paymentKey);
            })
                .isInstanceOf(PriceMismatchException.class);

            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.INITIALIZED);
        }
    }

    @DisplayName("결제 승인 테스트")
    @Nested
    class confirmPayment{

        @Test
        @DisplayName("결제 승인시, 결제 완료 상태로 변경한다.")
        void test2000(){
            // Given
            Payment payment = PaymentFixtures.verifiedPayment();
            String approvedAt = "2025-02-14T12:59:42+09:00";
            String parsingString = approvedAt.substring(0, approvedAt.indexOf("+"));
            // When
            payment.confirm("카드", approvedAt);

            // Then
            assertThat(payment).isNotNull();
            assertThat(payment.getMethod()).isEqualTo("카드");
            assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED);
            assertThat(payment.getApprovedAt()).isEqualTo(
                LocalDateTime.parse(parsingString, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
}