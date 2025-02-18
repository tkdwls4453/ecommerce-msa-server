package com.msa.order.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.order.adapter.in.web.dto.SimpleOrderResponse;
import com.msa.order.application.port.in.PrepareOrderUseCase;
import com.msa.order.application.port.in.OrderQueryUseCase;
import com.msa.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/orders")
public class OrderInternalController {

    private final OrderQueryUseCase orderQueryUseCase;
    private final PrepareOrderUseCase orderPrepareUseCase;

    @GetMapping("/{orderId}")
    public ApiResponse<SimpleOrderResponse> getSimpleOrder(
        @PathVariable Long orderId
    ){
        Order order = orderQueryUseCase.getOrderById(orderId);
        return ApiResponse.success(SimpleOrderResponse.from(order));
    }

    @PostMapping("/{orderId}/prepare")
    public ApiResponse<Void> prepareOrder(
        @PathVariable Long orderId
    ){
        orderPrepareUseCase.prepare(orderId);
        return ApiResponse.success();
    }
}
