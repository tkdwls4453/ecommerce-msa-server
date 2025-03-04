package com.msa.order.adapter.out;

import com.msa.common.exception.ExternalRequestException;
import com.msa.common.response.ApiResponse;
import com.msa.order.adapter.out.feign.DecreaseStockRequest;
import com.msa.order.adapter.out.feign.ProductFeignClient;
import com.msa.order.adapter.out.feign.RollbackStockRequest;
import com.msa.order.application.port.out.ProductStockManagePort;
import com.msa.order.domain.vo.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Qualifier("productFeignAdapter")
@RequiredArgsConstructor
//@Component
public class ProductFeignAdapter implements ProductStockManagePort {

    private final ProductFeignClient productFeignClient;

    @Override
    public void decreaseStock(List<OrderItem> orderLine) {
        DecreaseStockRequest request = DecreaseStockRequest.builder()
            .orderLine(orderLine)
            .build();

        try {
            ApiResponse<Void> response = productFeignClient.decreaseStock(request);
            log.info("[ProductFeignAdapter.decreaseStock] response: {}", response);
        }catch (Exception e){
            throw new ExternalRequestException(e.getMessage());
        }


    }

    @Override
    public void rollback(List<OrderItem> orderLine) {
        RollbackStockRequest request = RollbackStockRequest.builder()
            .orderLine(orderLine)
            .build();

        ApiResponse<Void> response = productFeignClient.rollbackStock(request);
        log.info("[ProductFeignAdapter.rollback] response: {}", response);
    }
}
