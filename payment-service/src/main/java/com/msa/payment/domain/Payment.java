package com.msa.payment.domain;

import com.msa.common.vo.Money;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.exception.OrderPermissionDeniedException;
import com.msa.payment.exception.PaymentOrderInvalidException;
import com.msa.payment.exception.PriceMismatchException;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
    private Long paymentId;
    private PayType payType;
    private PaymentStatus paymentStatus;
    private Money amount;
    private Long orderId;
    private String orderCode;
    private Long customerId;
    private String paymentKey;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;

    @Builder
    private Payment(Long paymentId, PayType payType, PaymentStatus paymentStatus, Money amount, Long orderId, String orderCode,
        Long customerId, String paymentKey, String failReason, boolean cancelYN,
        String cancelReason) {
        this.paymentId = paymentId;
        this.payType = payType;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.paymentKey = paymentKey;
        this.failReason = failReason;
        this.cancelYN = cancelYN;
        this.cancelReason = cancelReason;
    }

    public static Payment init(Long customerId, CreatePaymentCommand command) {
        return Payment.builder()
            .orderId(command.orderId())
            .paymentStatus(PaymentStatus.INITIALIZED)
            .customerId(customerId)
            .amount(command.amount())
            .build();
    }

    public void verifyAndPending(Long customerId, SimpleOrderResponse order, String paymentKey) {
        verifyCustomer(customerId, order);
        verifyOrder(customerId, order);
        verifyAmount(order.totalPrice());

        this.paymentKey = paymentKey;
        this.orderCode = order.orderCode();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    private static void verifyCustomer(Long customerId, SimpleOrderResponse order) {
        if(!Objects.equals(customerId, order.customerId())) {
            throw new OrderPermissionDeniedException();
        }
    }

    private void verifyOrder(Long orderId, SimpleOrderResponse order) {
        if (order == null) throw new PaymentOrderInvalidException();
        if(!order.orderStatus().equals("PAYMENT_PENDING")) throw new PaymentOrderInvalidException();
        if(!Objects.equals(orderId, order.orderId())) throw new PaymentOrderInvalidException();

    }

    private void verifyAmount(Money money) {
        if(!amount.equals(money)) {
            throw new PriceMismatchException();
        }
    }

    public String getStringPayType(){
        return this.getPayType() == null ? null : this.getPayType().toString();
    }


}

