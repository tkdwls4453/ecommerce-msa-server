package com.msa.payment.application.port.in.dto;

import com.msa.common.vo.Money;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import lombok.Builder;

@Builder
public record CreatePaymentCommand(
    Long orderId,
    Money amount
) {

    public static CreatePaymentCommand from(CreatePaymentRequest createPaymentRequest) {
        return CreatePaymentCommand.builder()
            .orderId(createPaymentRequest.orderId())
            .amount(createPaymentRequest.amount())
            .build();
    }

}
