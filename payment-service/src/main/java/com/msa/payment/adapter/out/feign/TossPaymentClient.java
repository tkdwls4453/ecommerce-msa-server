package com.msa.payment.adapter.out.feign;


import com.msa.payment.config.TossPaymentFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tossPaymentClient", url = "${payment.tosspayments.base-url}", configuration = TossPaymentFeignConfig.class)
public interface TossPaymentClient {

    @PostMapping(value = "/confirm", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> confirmPayment(@RequestBody TossPaymentConfirmRequest request);
}
