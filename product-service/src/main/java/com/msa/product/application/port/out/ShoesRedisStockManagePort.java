package com.msa.product.application.port.out;

import com.msa.product.application.port.in.OrderItem;
import java.util.List;

public interface ShoesRedisStockManagePort {

    void decreaseStock(List<OrderItem> orderLine);
    void rollbackStock(List<OrderItem> orderLine);
}
