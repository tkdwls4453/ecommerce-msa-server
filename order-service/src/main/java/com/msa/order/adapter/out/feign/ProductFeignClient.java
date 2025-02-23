package com.msa.order.adapter.out.feign;

import com.msa.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productFeignClient", url = "http://gateway-server:8080/internal/products")
public interface ProductFeignClient {

    @PostMapping(value = "/stock/decrease", consumes = "application/json")
    ApiResponse<Void> decreaseStock(@RequestBody DecreaseStockRequest request);

    @PostMapping(value = "/stock/rollback", consumes = "application/json")
    ApiResponse<Void> rollbackStock(@RequestBody RollbackStockRequest request);
}
