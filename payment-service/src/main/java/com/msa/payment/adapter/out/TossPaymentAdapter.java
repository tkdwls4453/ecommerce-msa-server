package com.msa.payment.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.vo.Money;
import com.msa.payment.adapter.out.feign.TossPaymentClient;
import com.msa.payment.adapter.out.feign.TossPaymentConfirmRequest;
import com.msa.payment.application.port.out.ExternalPaymentPort;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TossPaymentAdapter implements ExternalPaymentPort {

    private final TossPaymentClient tossPaymentClient;
    private final ObjectMapper objectMapper;

    @Override
    public ExternalPaymentResponse confirm(String paymentKey, String orderId, Money money)
        throws JsonProcessingException {

        TossPaymentConfirmRequest request = TossPaymentConfirmRequest.builder()
            .paymentKey(paymentKey)
            .orderId(orderId)
            .amount(money.amount().longValue())
            .build();

        String body = null;

        try{
            body = tossPaymentClient.confirmPayment(request).getBody();
        }catch (RuntimeException e){
            body = e.getMessage();
        }

        log.info("토스 페이먼츠 응답: {}", body);

        JsonNode rootNode = objectMapper.readTree(body);

        if(!rootNode.has("mId")){
            return ExternalPaymentResponse.builder()
                .status("FAIL")
                .failMessage(rootNode.get("message").asText())
                .build();
        }

        return ExternalPaymentResponse.builder()
            .paymentKey(rootNode.get("paymentKey").asText())
            .orderId(rootNode.get("orderId").asText())
            .status(rootNode.get("status").asText())
            .method(rootNode.get("method").asText())
            .totalAmount(rootNode.get("totalAmount").asLong())
            .approvedAt(rootNode.get("approvedAt").asText())
            .build();
    }
}
