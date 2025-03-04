package com.msa.product.application.service;

import com.msa.common.vo.Money;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.port.in.RollbackStockCommand;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import product.Product;
import product.Product.DecreaseStockRequest;
import product.Product.RollbackStockRequest;
import product.Product.StockResponse;
import product.StockServiceGrpc;

@RequiredArgsConstructor
@GrpcService
public class ShoesGrpcService extends StockServiceGrpc.StockServiceImplBase {

    private final ShoesRedisStockManageService shoesRedisStockManageService;

    @Override
    public void decreaseStock(DecreaseStockRequest request, StreamObserver<StockResponse> responseObserver) {
        DecreaseStockCommand command = getDecreaseStockCommand(request);
        String message = "success";
        try{
            shoesRedisStockManageService.decreaseStock(command);
        }catch (Exception e){
            message = e.getLocalizedMessage();
        }finally {
            responseObserver.onNext(StockResponse.newBuilder()
                .setMessage(message)
                .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void rollbackStock(RollbackStockRequest request, StreamObserver<StockResponse> responseObserver) {
        RollbackStockCommand command = getRollbackStockCommand(request);
        String message = "success";
        try{
            shoesRedisStockManageService.rollbackStock(command);
        }catch (Exception e){
            message = e.getMessage();
        }finally {
            responseObserver.onNext(StockResponse.newBuilder()
                .setMessage(message)
                .build());
            responseObserver.onCompleted();
        }
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

    private static RollbackStockCommand getRollbackStockCommand(RollbackStockRequest request) {
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

        return RollbackStockCommand.builder()
            .orderLine(orderLine)
            .build();
    }
}
