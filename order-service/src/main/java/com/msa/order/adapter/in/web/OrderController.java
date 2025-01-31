package com.msa.order.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.adapter.in.web.dto.CreatedNewOrderResponse;
import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.in.CreateNewOrderUseCase;
import com.msa.order.domain.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final CreateNewOrderUseCase createNewOrderUseCase;

    @PostMapping("/new")
    public ApiResponse<CreatedNewOrderResponse> newOrder(
        @RequestParam Long userId,
        @RequestBody @Valid CreateNewOrderRequest request
    ){
        Order newOrder = createNewOrderUseCase.createNewOrder(userId, CreateNewOrderCommand.builder()
            .orderLine(request.orderLine())
            .coupon(request.coupon())
            .shippingInfo(request.shippingInfo())
            .totalAmount(request.totalAmount())
            .build());

        return ApiResponse.success(CreatedNewOrderResponse.from(newOrder));
    }
}
