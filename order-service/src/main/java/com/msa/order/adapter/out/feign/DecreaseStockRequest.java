package com.msa.order.adapter.out.feign;

import com.msa.order.domain.vo.OrderItem;
import java.util.List;
import lombok.Builder;

@Builder
public record DecreaseStockRequest(
    List<OrderItem> orderLine
) {

}
