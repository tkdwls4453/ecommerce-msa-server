package com.msa.order.adapter.out;

import com.msa.order.adapter.out.grpc.ProductGrpcClient;
import com.msa.order.application.port.out.ProductStockManagePort;
import com.msa.order.domain.vo.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("productGrpcAdapter")
@RequiredArgsConstructor
@Component
public class ProductGrpcAdapter implements ProductStockManagePort {

    private final ProductGrpcClient productGrpcClient;

    @Override
    public void decreaseStock(List<OrderItem> orderLine) {
        productGrpcClient.decreaseStock(orderLine);
    }

    @Override
    public void rollback(List<OrderItem> orderLine) {
        productGrpcClient.rollbackStock(orderLine);
    }
}
