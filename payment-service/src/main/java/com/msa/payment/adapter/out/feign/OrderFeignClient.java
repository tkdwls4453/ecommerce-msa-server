package com.msa.payment.adapter.out.feign;

import com.msa.common.response.ApiResponse;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "orderFeinClient", url = "http://gateway-server:8080/internal/orders")
public interface OrderFeignClient {

    @GetMapping(value = "/{orderId}", consumes = "application/json")
    ApiResponse<SimpleOrderResponse> getSimpleOrderById(@PathVariable Long orderId);

    @PostMapping(value = "/{orderId}/prepare")
    ApiResponse<Void> prepareOrder(@PathVariable Long orderId);
}
