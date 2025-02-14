package com.msa.payment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TossPaymentFeignConfig {

    @Value("${payment.tosspayments.secret-key}")
    private String secretKey;

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor(){
        return requestTemplate -> {
            String credentials = secretKey + ":";
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            requestTemplate.header("Authorization", "Basic " + encodedCredentials);
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new TossPaymentErrorDecoder(objectMapper);
    }
}
