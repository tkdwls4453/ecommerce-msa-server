package com.msa.payment.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.common.vo.Money;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentCommandAdapterTest {

    @InjectMocks
    PaymentCommandAdapter sut;

    @Mock
    PaymentCommandJpaRepository paymentCommandJpaRepository;

    @Test
    @DisplayName("결제 도메인을 저장시, 도메인을 엔티티로 변경하여 저장후, 저장된 도메인을 리턴한다.")
    void save(){
        // Given
        Payment payment = PaymentFixtures.initedPayment();
        PaymentEntity paymentEntity = PaymentEntity.from(payment);

        when(paymentCommandJpaRepository.save(any(PaymentEntity.class)))
            .thenReturn(paymentEntity);

        // When
        Payment result = sut.save(payment);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(1L);
        assertThat(result.getOrderId()).isEqualTo(1L);
        assertThat(result.getOrderCode()).isEqualTo("test_order_code");
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getAmount()).isEqualTo(new Money(30000));
        assertThat(result.isCancelYN()).isFalse();
        verify(paymentCommandJpaRepository, times(1)).save(any(PaymentEntity.class));

    }
}