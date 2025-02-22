package com.msa.product.application.port.in;

import com.msa.product.adapter.in.web.dto.DecreaseStockRequest;
import java.util.List;
import lombok.Builder;

@Builder
public record DecreaseStockCommand(
    List<OrderItem> orderLine
) {

    public static DecreaseStockCommand from(DecreaseStockRequest request) {
        return DecreaseStockCommand.builder()
            .orderLine(request.orderLine())
            .build();
    }
}
