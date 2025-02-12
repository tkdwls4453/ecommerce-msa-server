package com.msa.payment.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.msa.common.vo.Money;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentFixtures;
import com.msa.payment.domain.PaymentStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentQueryAdapterTest {

    @InjectMocks
    PaymentQueryAdapter sut;

    @Mock
    PaymentQueryJpaRepository paymentQueryJpaRepository;

    @Test
    @DisplayName("결제 아이디로 결제 조회시, Optional.of(결제 정보)를 반환한다.")
    void test2000(){
        // Given
        Long paymentId = 1L;
        PaymentEntity mockPayment = PaymentFixtures.paymentEntity();

        when(paymentQueryJpaRepository.findById(paymentId))
            .thenReturn(Optional.of(PaymentFixtures.paymentEntity()));

        // When
        Optional<Payment> result = sut.findById(paymentId);
        Payment payment = result.orElseGet(null);

        // Then
        assertThat(payment).isNotNull();
        assertThat(payment.getPaymentId()).isEqualTo(paymentId);
        assertThat(payment.getAmount()).isEqualTo(new Money(30000));
        assertThat(payment.getOrderId()).isEqualTo(1L);
        assertThat(payment.getOrderCode()).isEqualTo("test_order_code");
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.INITIALIZED);
        assertThat(payment.getCustomerId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 결제 아이디로 결제 조회시, Optional.empty() 을 반환한다.")
    void test2001(){
        // Given
        Long paymentId = 1L;

        when(paymentQueryJpaRepository.findById(paymentId))
            .thenReturn(Optional.empty());

        // When
        Optional<Payment> result = sut.findById(paymentId);

        // Then
        assertThat(result.isPresent()).isFalse();
    }
}