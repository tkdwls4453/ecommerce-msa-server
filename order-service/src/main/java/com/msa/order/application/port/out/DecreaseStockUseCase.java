package com.msa.order.application.port.out;

import com.msa.order.domain.vo.OrderItem;
import java.util.List;

public interface DecreaseStockUseCase {

    void decreaseStock(List<OrderItem> orderLine);

    void rollback(List<OrderItem> orderLine);
}
