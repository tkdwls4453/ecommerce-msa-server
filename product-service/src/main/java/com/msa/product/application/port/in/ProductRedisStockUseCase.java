package com.msa.product.application.port.in;


public interface ProductRedisStockUseCase {

    void decreaseStock(DecreaseStockCommand command);

    void rollbackStock(RollbackStockCommand command);
}
