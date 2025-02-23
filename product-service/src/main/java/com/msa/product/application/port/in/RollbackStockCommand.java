package com.msa.product.application.port.in;

import com.msa.product.adapter.in.web.dto.DecreaseStockRequest;
import com.msa.product.adapter.in.web.dto.RollbackStockRequest;
import java.util.List;
import lombok.Builder;

@Builder
public record RollbackStockCommand(
    List<OrderItem> orderLine
) {

    public static RollbackStockCommand from(RollbackStockRequest request) {
        return RollbackStockCommand.builder()
            .orderLine(request.orderLine())
            .build();
    }
}
