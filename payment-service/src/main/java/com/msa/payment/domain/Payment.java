package com.msa.payment.domain;

import com.msa.common.vo.Money;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.exception.OrderPermissionDeniedException;
import com.msa.payment.exception.PaymentOrderInvalidException;
import com.msa.payment.exception.PriceMismatchException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
    private Long paymentId;
    private String method;
    private PaymentStatus paymentStatus;
    private Money amount;
    private Long orderId;
    private String orderCode;
    private Long customerId;
    private String paymentKey;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private LocalDateTime approvedAt;

    @Builder
    private Payment(Long paymentId, String method, PaymentStatus paymentStatus, Money amount, Long orderId, String orderCode,
        Long customerId, String paymentKey, String failReason, boolean cancelYN,
        String cancelReason) {
        this.paymentId = paymentId;
        this.method = method;
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

    public void confirm(String method, String approvedAt) {
        this.method = method;
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.approvedAt = OffsetDateTime.parse(approvedAt + "z").toLocalDateTime();
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

    public void fail(String failMessage) {
        this.paymentStatus = PaymentStatus.FAILED;
        this.failReason = failMessage;
    }

    public String getPaymentStatusAsString(){
        return this.paymentStatus == null ? null : paymentStatus.toString();
    }
}

