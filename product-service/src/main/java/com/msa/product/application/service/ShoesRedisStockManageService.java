package com.msa.product.application.service;

import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.ProductRedisStockUseCase;
import com.msa.product.application.port.in.RollbackStockCommand;
import com.msa.product.application.port.out.ShoesRedisStockManagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ShoesRedisStockManageService implements ProductRedisStockUseCase {

    private final ShoesRedisStockManagePort shoesRedisStockManagePort;

    @Override
    public void decreaseStock(DecreaseStockCommand command) {
        shoesRedisStockManagePort.decreaseStock(command.orderLine());
    }

    @Override
    public void rollbackStock(RollbackStockCommand command) {
        shoesRedisStockManagePort.rollbackStock(command.orderLine());
    }
}
