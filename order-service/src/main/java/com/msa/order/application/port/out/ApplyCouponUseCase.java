package com.msa.order.application.port.out;

import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.Money;

public interface ApplyCouponUseCase {

    void applyCoupon(Money beforePrice, Money appliedPrice, AppliedCoupon coupon);

}
