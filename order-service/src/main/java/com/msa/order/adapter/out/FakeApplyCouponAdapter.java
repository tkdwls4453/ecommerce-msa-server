package com.msa.order.adapter.out;

import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.order.domain.vo.AppliedCoupon;
import com.msa.order.domain.vo.Money;
import org.springframework.stereotype.Component;

@Component
public class FakeApplyCouponAdapter implements ApplyCouponUseCase {


    @Override
    public void applyCoupon(Money beforePrice, Money appliedPrice, AppliedCoupon coupon) {

    }
}
