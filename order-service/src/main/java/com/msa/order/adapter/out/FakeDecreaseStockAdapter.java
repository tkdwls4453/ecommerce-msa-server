package com.msa.order.adapter.out;

import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.domain.vo.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FakeDecreaseStockAdapter implements DecreaseStockUseCase {

    @Override
    public void decreaseStock(List<OrderItem> orderLine) {
    }

    @Override
    public void rollback(List<OrderItem> orderLine) {

    }
}
