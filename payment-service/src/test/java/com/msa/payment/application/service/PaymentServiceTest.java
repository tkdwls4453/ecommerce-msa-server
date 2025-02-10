package com.msa.payment.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.common.vo.Money;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.application.port.out.CommandPaymentPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.QueryOrderPort;
import com.msa.payment.domain.OrderFixtures;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentFixtures;
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
    private QueryOrderPort queryOrderPort;

    @Mock
    private CommandPaymentPort commandPaymentPort;
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
                .amount(30000)
                .build();

            CreatePaymentCommand command = CreatePaymentCommand.from(request);
            SimpleOrderResponse simpleOrderResponse = OrderFixtures.simpleOrderResponse();

            when(queryOrderPort.findSimpleOrderByOrderId(any(Long.class)))
                .thenReturn(simpleOrderResponse);

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

            verify(queryOrderPort, times(1)).findSimpleOrderByOrderId(any(Long.class));
            verify(commandPaymentPort, times(1)).save(any(Payment.class));
        }
    }
}