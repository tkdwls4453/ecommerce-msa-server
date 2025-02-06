package com.msa.order.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ShippingInfo(
    @NotBlank(message = "수신자 이름은 필수입니다.")
    String recipientName,

    @NotBlank(message = "배송지 주소는 필수입니다.")
    String address
) {
}
