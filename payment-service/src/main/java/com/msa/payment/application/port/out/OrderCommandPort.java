package com.msa.payment.application.port.out;

public interface OrderCommandPort {

    void changeToPreparing(Long orderId);
}
