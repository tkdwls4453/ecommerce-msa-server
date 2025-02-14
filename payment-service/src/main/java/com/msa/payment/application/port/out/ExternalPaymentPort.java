package com.msa.payment.application.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.common.vo.Money;
import com.msa.payment.application.port.out.dto.ExternalPaymentResponse;

public interface ExternalPaymentPort {

    ExternalPaymentResponse confirm(String paymentKey, String orderCode, Money amount)
        throws JsonProcessingException;
}
