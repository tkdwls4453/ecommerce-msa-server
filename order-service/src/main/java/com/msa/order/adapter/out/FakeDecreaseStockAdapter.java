package com.msa.order.adapter.out;

import com.msa.order.application.port.out.ProductStockManagePort;
import com.msa.order.domain.vo.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

public class FakeDecreaseStockAdapter implements ProductStockManagePort {

    @Override
    public void decreaseStock(List<OrderItem> orderLine) {
    }

    @Override
    public void rollback(List<OrderItem> orderLine) {

    }
}
