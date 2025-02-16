package com.msa.payment.adapter.out;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.vo.Money;
import com.msa.payment.adapter.out.feign.TossPaymentClient;
import com.msa.payment.adapter.out.feign.TossPaymentConfirmRequest;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TossPaymentAdapterTest {

    @InjectMocks
    private TossPaymentAdapter sut;

    @Mock
    private TossPaymentClient tossPaymentClient;

    @Mock
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("토스페이먼츠 승인 요청을 처리 테스트")
    class confirm{
        @Test
        @DisplayName("토스페이먼츠 승인을 요청하고 성공하면 성공 응답을 생성하여 반환한다.")
        void test2000() throws JsonProcessingException {
            // Given
            String paymentKey = "test_payment_key";
            String orderId = "test_order_code";
            Money money = new Money(30000);

            String responseBody = "{\n"
                + "  \"mId\": \"tgen_docs\",\n"
                + "  \"paymentKey\": \"test_payment_key\",\n"
                + "  \"orderId\": \"test_order_code\",\n"
                + "  \"status\": \"DONE\",\n"
                + "  \"approvedAt\": \"2025-02-13T18:22:00+09:00\",\n"
                + "  \"totalAmount\": 30000,\n"
                + "  \"method\": \"카드\"\n"
                + "}";

            ResponseEntity<String> response = ResponseEntity.ok(responseBody);
            when(tossPaymentClient.confirmPayment(any(TossPaymentConfirmRequest.class)))
                .thenReturn(response);

            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            when(objectMapper.readTree(responseBody)).thenReturn(jsonNode);

            // When
            ExternalPaymentResponse result = sut.confirm(paymentKey, orderId, money);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.paymentKey()).isEqualTo(paymentKey);
            assertThat(result.orderId()).isEqualTo(orderId);
            assertThat(result.status()).isEqualTo("DONE");
            assertThat(result.totalAmount()).isEqualTo(30000);
            assertThat(result.method()).isEqualTo("카드");
            assertThat(result.approvedAt()).isEqualTo("2025-02-13T18:22:00+09:00");

            verify(tossPaymentClient, times(1)).confirmPayment(any(TossPaymentConfirmRequest.class));
            verify(objectMapper, times(1)).readTree(response.getBody());
        }

        @Test
        @DisplayName("토스페이먼츠 승인을 요청하고 실패하면 실패 응답을 생성하여 반환한다.")
        void test2001() throws JsonProcessingException {
            // Given
            String paymentKey = "test_payment_key";
            String orderId = "test_order_code";
            Money money = new Money(30000);

            String responseBody = "{\n"
                + "  \"code\": \"NOT_FOUND_PAYMENT_SESSION\",\n"
                + "  \"message\": \"결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다.\"\n"
                + "}";

            ResponseEntity<String> response = ResponseEntity.badRequest().body(responseBody);
            when(tossPaymentClient.confirmPayment(any(TossPaymentConfirmRequest.class)))
                .thenReturn(response);

            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            when(objectMapper.readTree(responseBody)).thenReturn(jsonNode);

            // When
            ExternalPaymentResponse result = sut.confirm(paymentKey, orderId, money);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.status()).isEqualTo("FAIL");
            assertThat(result.failMessage()).isEqualTo("결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다.");


            verify(tossPaymentClient, times(1)).confirmPayment(any(TossPaymentConfirmRequest.class));
            verify(objectMapper, times(1)).readTree(response.getBody());
        }
    }
}