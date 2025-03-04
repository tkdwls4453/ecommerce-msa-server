package com.msa.payment.adapter.out;

import com.msa.common.exception.ExternalRequestException;
import com.msa.common.response.ApiResponse;
import com.msa.payment.adapter.out.feign.OrderFeignClient;
import com.msa.payment.application.port.out.OrderCommandPort;
import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderFeignAdapter implements OrderQueryPort, OrderCommandPort {

    private final OrderFeignClient orderFeignClient;

    @Override
    public SimpleOrderResponse findSimpleOrderByOrderId(Long orderId) {
        ApiResponse<SimpleOrderResponse> response;

        try{
             response = orderFeignClient.getSimpleOrderById(orderId);
        }catch (Exception e){
            throw new ExternalRequestException(e.getMessage());
        }

        log.info("데이터: {}",response);
        return response.getData();
    }

    @Override
    public void changeToPreparing(Long orderId) {
        orderFeignClient.prepareOrder(orderId);
    }


}
