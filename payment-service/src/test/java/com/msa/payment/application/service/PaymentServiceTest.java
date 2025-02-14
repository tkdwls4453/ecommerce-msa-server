package com.msa.payment.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.common.vo.Money;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.adapter.in.web.dto.VerifyPaymentRequest;
import com.msa.payment.application.port.in.dto.VerifyPaymentCommand;
import com.msa.payment.application.port.out.ExternalPaymentPort;
import com.msa.payment.application.port.out.OrderCommandPort;
import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.application.port.out.PaymentQueryPort;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.domain.OrderFixtures;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentFixtures;
import com.msa.payment.domain.PaymentStatus;
import com.msa.payment.exception.OrderPermissionDeniedException;
import com.msa.payment.exception.PaymentNotFoundException;
import com.msa.payment.exception.PaymentOrderInvalidException;
import com.msa.payment.exception.PriceMismatchException;
import java.time.LocalDate;
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
class PaymentServiceTest {

    @InjectMocks
    private PaymentService sut;

    @Mock
    private OrderQueryPort orderQueryPort;

    @Mock
    private OrderCommandPort orderCommandPort;

    @Mock
    private PaymentCommandPort commandPaymentPort;

    @Mock
    private PaymentQueryPort paymentQueryPort;

    @Mock
    private ExternalPaymentPort externalPaymentPort;

    /**
     * 결제 시도 서비스 기능
     * 입력: customerId, 주문 생성 커맨드 (orderId, amount)
     * 출력: 초기화된 결제 도메인
     * 행동
     *  - 주문 정보 조회 (주문 서비스)
     *  - 주문 검증
     *  - 유저 검증
     *  - 결제 초기화 (생성)
     *  - 초기화된 결제 저장
     */

    @Nested
    @DisplayName("[SERVICE] 결제 시도 테스트")
    class tryPayment{
        @Test
        @DisplayName("정상적으로 결제 시도되면, 결제를 초기화 후 저장한다.")
        void test2000(){
            // Given
            Long customerId = 1L;
            CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(1L)
                .amount(new Money(30000))
                .build();

            CreatePaymentCommand command = CreatePaymentCommand.from(request);

            when(commandPaymentPort.save(any(Payment.class)))
                .thenReturn(PaymentFixtures.initedPayment());

            // When
            Payment result = sut.tryPayment(customerId, command);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getOrderId()).isEqualTo(1L);
            assertThat(result.getOrderCode()).isEqualTo("test_order_code");
            assertThat(result.getAmount()).isEqualTo(new Money(30000));
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getPaymentId()).isEqualTo(1L);

            verify(commandPaymentPort, times(1)).save(any(Payment.class));
        }
    }

    @Nested
    @DisplayName("[SERVICE] 결제 정보 검증 테스트")
    class verify{
        @Test
        @DisplayName("결제 검증을 시도하면 유저 정보, 주문 정보를 검증 후 결제 정보를 수정하고 검증된 결제를 반환한다.")
        void test2000(){
            // Given
            Long customerId = 1L;
            VerifyPaymentRequest request = VerifyPaymentRequest.builder()
                .paymentId(1L)
                .orderId(1L)
                .paymentKey("test_payment_key")
                .build();

            VerifyPaymentCommand command = VerifyPaymentCommand.from(request);

            when(paymentQueryPort.findById(any(Long.class)))
                .thenReturn(Optional.of(PaymentFixtures.initedPayment()));

            when(orderQueryPort.findSimpleOrderByOrderId(any(Long.class)))
                .thenReturn(OrderFixtures.simpleOrderResponse());

            when(commandPaymentPort.save(any(Payment.class)))
                .thenReturn(PaymentFixtures.verifiedPayment());

            // When
            Payment result = sut.verify(customerId, command);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getOrderId()).isEqualTo(1L);
            assertThat(result.getOrderCode()).isEqualTo("test_order_code");
            assertThat(result.getPaymentKey()).isEqualTo("test_payment_key");
            assertThat(result.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
            assertThat(result.getAmount()).isEqualTo(new Money(30000));
            assertThat(result.getCustomerId()).isEqualTo(customerId);
            assertThat(result.getPaymentId()).isEqualTo(1L);

            verify(paymentQueryPort, times(1)).findById(any(Long.class));
            verify(orderQueryPort, times(1)).findSimpleOrderByOrderId(any(Long.class));
            verify(commandPaymentPort, times(1)).save(any(Payment.class));
        }

        @Test
        @DisplayName("결제 검증 요청시, 주문 유저와 결제 유저가 다르면 예외를 발생시킨다.")
        void test1(){
            // Given
            Long customerId = 2L;
            VerifyPaymentRequest request = VerifyPaymentRequest.builder()
                .paymentId(1L)
                .orderId(1L)
                .paymentKey("test_payment_key")
                .build();

            VerifyPaymentCommand command = VerifyPaymentCommand.from(request);

            when(paymentQueryPort.findById(any(Long.class)))
                .thenReturn(Optional.of(PaymentFixtures.initedPayment()));

            when(orderQueryPort.findSimpleOrderByOrderId(any(Long.class)))
                .thenReturn(OrderFixtures.simpleOrderResponse(1L, 2L, new Money(30000)));

            // When Then
            assertThatThrownBy(
                () -> sut.verify(customerId, command)
            )
                .isInstanceOf(PaymentOrderInvalidException.class);
        }

        @Test
        @DisplayName("결제 검증 요청시, 주문 금액 정보와 결제 금액 정보가 다르면 예외를 발생시킨다.")
        void test2(){
            // Given
            Long customerId = 1L;
            VerifyPaymentRequest request = VerifyPaymentRequest.builder()
                .paymentId(1L)
                .orderId(1L)
                .paymentKey("test_payment_key")
                .build();

            VerifyPaymentCommand command = VerifyPaymentCommand.from(request);

            when(paymentQueryPort.findById(any(Long.class)))
                .thenReturn(Optional.of(PaymentFixtures.initedPayment()));

            when(orderQueryPort.findSimpleOrderByOrderId(any(Long.class)))
                .thenReturn(OrderFixtures.simpleOrderResponse(1L, 1L, new Money(3000)));

            // When Then

            assertThatThrownBy(
                () -> sut.verify(customerId, command)
            )
                .isInstanceOf(PriceMismatchException.class);
        }

        @Test
        @DisplayName("결제 검증 요청시, 잘못된 주문 아이디라면 예외를 발생시킨다.")
        void test3(){
            // Given
            Long customerId = 1L;
            Long wrongOrderId = 2L;
            VerifyPaymentRequest request = VerifyPaymentRequest.builder()
                .paymentId(1L)
                .orderId(1L)
                .paymentKey("test_payment_key")
                .build();

            VerifyPaymentCommand command = VerifyPaymentCommand.from(request);

            when(paymentQueryPort.findById(any(Long.class)))
                .thenReturn(Optional.of(PaymentFixtures.initedPayment()));

            when(orderQueryPort.findSimpleOrderByOrderId(any(Long.class)))
                .thenReturn(OrderFixtures.simpleOrderResponse(wrongOrderId, 1L, new Money(30000)));

            // When Then
            assertThatThrownBy(
                () -> sut.verify(customerId, command)
            )
                .isInstanceOf(PaymentOrderInvalidException.class);
        }

        @Test
        @DisplayName("결제 검증 요청시, 존재하지 않은 결제 아이디라면 예외를 발생시킨다.")
        void test4(){
            // Given
            Long customerId = 1L;
            Long wrongPaymentId = 200L;
            VerifyPaymentRequest request = VerifyPaymentRequest.builder()
                .paymentId(wrongPaymentId)
                .orderId(1L)
                .paymentKey("test_payment_key")
                .build();

            VerifyPaymentCommand command = VerifyPaymentCommand.from(request);

            when(paymentQueryPort.findById(any(Long.class)))
                .thenReturn(Optional.empty());

            // When Then
            assertThatThrownBy(
                () -> sut.verify(customerId, command)
            )
                .isInstanceOf(PaymentNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("[SERVICE] 결제 승인 테스트")
    class confirm{
        @Test
        @DisplayName("결제 승인을 시도하면 외부 결제 시스템을 통해 결제 승인 후 결제 정보 상태를 갱신 후 반환한다.")
        void test2000() throws JsonProcessingException {
            // Given
            Long paymentId = 1L;
            Payment payment = PaymentFixtures.verifiedPayment();
            LocalDateTime approvedAt = LocalDateTime.now();

            ExternalPaymentResponse response = ExternalPaymentResponse.builder()
                .paymentKey(payment.getPaymentKey())
                .orderId(String.valueOf(payment.getOrderId()))
                .status("DONE")
                .totalAmount(30000L)
                .method("카드")
                .approvedAt(approvedAt.toString())
                .build();

            when(paymentQueryPort.findById(paymentId)).thenReturn(Optional.of(payment));
            when(externalPaymentPort.confirm(payment.getPaymentKey(), payment.getOrderCode(), payment.getAmount()))
                .thenReturn(response);

            when(commandPaymentPort.save(any(Payment.class)))
                .thenReturn(PaymentFixtures.payment(PaymentStatus.COMPLETED, "카드", null));

            // When
            Payment result = sut.confirm(paymentId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED);
            assertThat(result.getMethod()).isEqualTo("카드");
            assertThat(result.getAmount()).isEqualTo(new Money(30000));

            verify(paymentQueryPort, times(1)).findById(paymentId);
            verify(externalPaymentPort, times(1)).confirm(payment.getPaymentKey(), payment.getOrderCode(), payment.getAmount());
            verify(orderCommandPort, times(1)).changeToPreparing(paymentId);
            verify(commandPaymentPort, times(1)).save(any(Payment.class));
        }

        @Test
        @DisplayName("결제 승인 시도시 존재하지 않는 결제 아이디로 요청하면 예외를 반환한다.")
        void test1() {
            // Given
            Long wrongPaymentId = 2L;

            when(paymentQueryPort.findById(wrongPaymentId)).thenReturn(Optional.empty());

            // When Then
            assertThatThrownBy(() -> {
                sut.confirm(wrongPaymentId);
            })
                .isInstanceOf(PaymentNotFoundException.class);


            verify(paymentQueryPort, times(1)).findById(wrongPaymentId);
        }

        @Test
        @DisplayName("결제 승인을 시도할 경우 외부 결제 시스템 결제에 실패하면 결제를 실패 상태로 변경 후 반환한다.")
        void test3() throws JsonProcessingException {
            // Given
            Long paymentId = 1L;
            Payment payment = PaymentFixtures.verifiedPayment();
            String failReason = "결제 세션이 만료됐습니다.";
            ExternalPaymentResponse failResponse = ExternalPaymentResponse.builder()
                .status("FAIL")
                .failMessage(failReason)
                .build();

            when(paymentQueryPort.findById(paymentId)).thenReturn(Optional.of(payment));
            when(externalPaymentPort.confirm(payment.getPaymentKey(), payment.getOrderCode(), payment.getAmount()))
                .thenReturn(failResponse);

            when(commandPaymentPort.save(any(Payment.class)))
                .thenReturn(PaymentFixtures.payment(PaymentStatus.FAILED, null, failReason));

            // When
            Payment result = sut.confirm(paymentId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
            assertThat(result.getFailReason()).isEqualTo(failReason);

            verify(paymentQueryPort, times(1)).findById(paymentId);
            verify(externalPaymentPort, times(1)).confirm(payment.getPaymentKey(), payment.getOrderCode(), payment.getAmount());
            verify(orderCommandPort, times(0)).changeToPreparing(paymentId);
            verify(commandPaymentPort, times(1)).save(any(Payment.class));
        }
    }
}