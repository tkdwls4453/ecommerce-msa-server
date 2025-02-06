package com.msa.order.application.port.out;

import com.msa.common.vo.Money;

public interface ApplyCouponUseCase {

    void applyCoupon(Money beforePrice, Money appliedPrice, Long couponId);

}
