package com.msa.product.application.service;

import com.msa.common.vo.Money;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import product.Product;
import product.Product.DecreaseStockRequest;
import product.Product.EmptyResponse;
import product.Product.RollbackStockRequest;
import product.StockServiceGrpc;

@RequiredArgsConstructor
@GrpcService
public class ShoesGrpcService extends StockServiceGrpc.StockServiceImplBase {

    private final ShoesRedisStockManageService shoesRedisStockManageService;

    @Override
    public void decreaseStock(DecreaseStockRequest request, StreamObserver<EmptyResponse> responseObserver) {
        DecreaseStockCommand command = getDecreaseStockCommand(request);
        shoesRedisStockManageService.decreaseStock(command);
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void rollbackStock(RollbackStockRequest request,
        StreamObserver<EmptyResponse> responseObserver) {
        super.rollbackStock(request, responseObserver);
    }

    private static DecreaseStockCommand getDecreaseStockCommand(DecreaseStockRequest request) {
        List<OrderItem> orderLine = new ArrayList<>();

        for (Product.OrderItem item : request.getOrderLineList()) {
            orderLine.add(OrderItem.builder()
                .idx(item.getIdx())
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .quantity(item.getQuantity())
                .price(new Money(item.getPrice()))
                .build());
        }

        return DecreaseStockCommand.builder()
            .orderLine(orderLine)
            .build();
    }
}
