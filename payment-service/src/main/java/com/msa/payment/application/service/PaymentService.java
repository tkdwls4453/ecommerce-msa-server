package com.msa.payment.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.payment.application.port.in.PaymentCommandUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.in.dto.VerifyPaymentCommand;
import com.msa.payment.application.port.out.ExternalPaymentPort;
import com.msa.payment.application.port.out.OrderCommandPort;
import com.msa.payment.application.port.out.OrderQueryPort;
import com.msa.payment.application.port.out.PaymentCommandPort;
import com.msa.payment.application.port.out.PaymentQueryPort;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;
import com.msa.payment.application.port.out.dto.SimpleOrderResponse;
import com.msa.payment.domain.Payment;
import com.msa.payment.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentCommandUseCase {

    private final PaymentCommandPort paymentCommandPort;
    private final PaymentQueryPort paymentQueryPort;
    private final OrderQueryPort orderQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final ExternalPaymentPort externalPaymentPort;

    @Override
    public Payment verify(Long customerId, VerifyPaymentCommand command) {
        Payment payment = paymentQueryPort.findById(command.orderId())
            .orElseThrow(PaymentNotFoundException::new);

        SimpleOrderResponse order = orderQueryPort.findSimpleOrderByOrderId(command.orderId());

        payment.verifyAndPending(customerId, order, command.paymentKey());
        paymentCommandPort.save(payment);

        return payment;
    }

    @Override
    public Payment tryPayment(Long customerId, CreatePaymentCommand command) {
        Payment initedPayment = Payment.init(customerId, command);

        return paymentCommandPort.save(initedPayment);
    }

    @Override
    public Payment confirm(Long paymentId) throws JsonProcessingException {
        Payment payment = paymentQueryPort.findById(paymentId)
            .orElseThrow(PaymentNotFoundException::new);

        // 외부 결제 통신
        ExternalPaymentResponse response = externalPaymentPort.confirm(payment.getPaymentKey(), payment.getOrderCode(), payment.getAmount());

        if(response.status().equals("FAIL")){
            payment.fail(response.failMessage());
        }else{
            payment.confirm(response.method(), response.approvedAt());

            // 주문 상태 변경 요청 (결제 대기 -> 준비중)
            orderCommandPort.changeToPreparing(payment.getOrderId());
        }

        return paymentCommandPort.save(payment);
    }
}
