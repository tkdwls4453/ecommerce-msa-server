package com.msa.payment.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.payment.adapter.in.web.dto.CreatePaymentRequest;
import com.msa.payment.adapter.in.web.dto.PaymentResponse;
import com.msa.payment.adapter.in.web.dto.VerifyPaymentRequest;
import com.msa.payment.application.port.in.PaymentCommandUseCase;
import com.msa.payment.application.port.in.dto.CreatePaymentCommand;
import com.msa.payment.application.port.in.dto.VerifyPaymentCommand;
import com.msa.payment.domain.Payment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentCommandUseCase commandPaymentUseCase;

    @PostMapping
    public ApiResponse<PaymentResponse> tryPayment(
        @RequestParam(value = "customerId") Long customerId,
        @Valid @RequestBody CreatePaymentRequest request
    ){
        Payment initedPayment = commandPaymentUseCase.tryPayment(customerId, CreatePaymentCommand.from(request));
        return ApiResponse.success(PaymentResponse.from(initedPayment));
    }

    @PostMapping("/verify")
    public ApiResponse<PaymentResponse> verifyPayment(
        @RequestParam(value = "customerId") Long customerId,
        @Valid @RequestBody VerifyPaymentRequest request
    ){
        Payment verifiedPayment = commandPaymentUseCase.verify(customerId, VerifyPaymentCommand.from(request));
        return ApiResponse.success(PaymentResponse.from(verifiedPayment));
    }

}
