package com.msa.payment.application.port.out.dto;

import com.msa.common.vo.Money;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SimpleOrderResponse(
    Long orderId,
    String orderCode,
    Long customerId,
    String orderStatus,
    Money totalPrice,
    LocalDateTime orderTime
) {

}
