package com.msa.payment.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.application.port.in.PaymentCommandUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.domain.Payment;
import com.msa.payment.domain.PaymentFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentCommandUseCase commandPaymentUseCase;

    @MockitoBean
    private PaymentCommandPort paymentCommandPort;

    /**
     * 결제 시도 api
     * 입력 :
     *  - 유저 아이디 (userId) , param
     *  - 주문 아이디 (orderId)
     *  - 금액 (amount)
     * 응답 :
     *  - 결제 아이디 (paymentId)
     *  - 유저 아이디 (userId)
     *  - 주문 아이디 (orderId)
     *  - 주문 코드 (orderCod)
     *  - 금액 (amount)
     */

    @Nested
    @DisplayName("POST /payments")
    class tryPayment{

        @Test
        @DisplayName("결제 정보로 결제 시도 요청시, 주문 상태와 유저 정보를 확인하고 정상인 경우 200OK 를 리턴한다.")
        void test2000() throws Exception {
            // Given
            CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(1L)
                .amount(30000)
                .build();

            Payment initedPayment = PaymentFixtures.initedPayment();

            String body = objectMapper.writeValueAsString(request);
            when(commandPaymentUseCase.tryPayment(any(Long.class), any(CreatePaymentCommand.class)))
                .thenReturn(initedPayment);

            // When Then
            mockMvc.perform(
                post("/payments")
                    .param("customerId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"))
                .andExpect(jsonPath("$.data.paymentId").value("1"))
                .andExpect(jsonPath("$.data.orderId").value("1"))
                .andExpect(jsonPath("$.data.customerId").value("1"))
                .andExpect(jsonPath("$.data.orderCode").value("test_order_code"))
                .andExpect(jsonPath("$.data.amount").value(30000))
            ;

            verify(commandPaymentUseCase, times(1)).tryPayment(any(Long.class), any(CreatePaymentCommand.class));
        }

        @Test
        @DisplayName("주문 아이디 없이 결제 시도시, 400 BAD_REQUEST 를 응답한다.")
        void test1() throws Exception {
            // Given
            CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(null)
                .amount(30000)
                .build();

            String body = objectMapper.writeValueAsString(request);


            // When Then
            mockMvc.perform(
                    post("/payments")
                        .param("customerId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.orderId").value("주문 아이디는 필수입니다."));
        }

        @Test
        @DisplayName("주문 금액 없이 결제 시도시, 400 BAD_REQUEST 를 응답한다.")
        void test2() throws Exception {
            // Given
            CreatePaymentRequest request = CreatePaymentRequest.builder()
                .orderId(1L)
                .amount(null)
                .build();

            String body = objectMapper.writeValueAsString(request);


            // When Then
            mockMvc.perform(
                    post("/payments")
                        .param("customerId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.amount").value("결제 금액 정보는 필수입니다."));
        }
    }
}