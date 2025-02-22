package com.msa.product.application.port.in;


public interface ProductStockUseCase {

    void decreaseStock(DecreaseStockCommand command);
}
