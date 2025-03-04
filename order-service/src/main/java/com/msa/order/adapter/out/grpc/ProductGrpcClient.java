package com.msa.order.adapter.out.grpc;

import com.msa.order.domain.vo.OrderItem;
import java.util.ArrayList;
import java.util.List;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import product.Product;
import product.Product.EmptyResponse;
import product.StockServiceGrpc;

@Service
public class ProductGrpcClient {

    @GrpcClient("grpc-server")
    private StockServiceGrpc.StockServiceBlockingStub stockServiceBlockingStub;

    public void decreaseStock(List<OrderItem> orderLine){
        List<Product.OrderItem> grpcOrderLine = new ArrayList<>();

        for (OrderItem orderItem : orderLine) {
            grpcOrderLine.add(Product.OrderItem.newBuilder()
                    .setItemId(orderItem.itemId())
                    .setQuantity(orderItem.quantity())
                .build());
        }

        Product.DecreaseStockRequest grpcRequest = Product.DecreaseStockRequest.newBuilder()
            .addAllOrderLine(grpcOrderLine)
            .build();

        EmptyResponse emptyResponse = this.stockServiceBlockingStub.decreaseStock(grpcRequest);
    }

    public void rollbackStock(List<OrderItem> orderLine){
        List<Product.OrderItem> grpcOrderLine = new ArrayList<>();

        for (OrderItem orderItem : orderLine) {
            grpcOrderLine.add(Product.OrderItem.newBuilder()
                .setItemId(orderItem.itemId())
                .setQuantity(orderItem.quantity())
                .build());
        }

        Product.RollbackStockRequest grpcRequest = Product.RollbackStockRequest.newBuilder()
            .addAllOrderLine(grpcOrderLine)
            .build();

        EmptyResponse emptyResponse = this.stockServiceBlockingStub.rollbackStock(grpcRequest);
    }
}
