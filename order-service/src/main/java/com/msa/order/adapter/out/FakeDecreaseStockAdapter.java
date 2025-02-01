package com.msa.order.adapter.out;

import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.domain.vo.OrderShoes;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FakeDecreaseStockAdapter implements DecreaseStockUseCase {

    @Override
    public void decreaseStock(List<OrderShoes> orderLine) {
    }

    @Override
    public void rollback(List<OrderShoes> orderLine) {

    }
}
