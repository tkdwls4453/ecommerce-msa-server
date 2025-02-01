package com.msa.order.application.port.out;

import com.msa.order.domain.vo.OrderShoes;
import java.util.List;

public interface DecreaseStockUseCase {

    void decreaseStock(List<OrderShoes> orderLine);

    void rollback(List<OrderShoes> orderLine);
}
