package com.msa.product.application.port.in;

import com.msa.common.vo.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderItem(
    int idx,
    @NotNull(message = "상품 아이디는 필수입니다.")
    Long itemId,
    String itemName,
    @NotNull(message = "주문 수량은 필수입니다.")
    Integer quantity,
    Money price
) {

}
