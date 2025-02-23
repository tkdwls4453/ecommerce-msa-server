package com.msa.product.adapter.in.web.dto;

import com.msa.product.application.port.in.OrderItem;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Builder;

@Builder
public record RollbackStockRequest(
    @Valid List<OrderItem> orderLine
) {

}
