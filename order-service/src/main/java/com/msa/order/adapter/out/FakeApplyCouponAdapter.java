package com.msa.order.adapter.out;

import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.common.vo.Money;
import org.springframework.stereotype.Component;

@Component
public class FakeApplyCouponAdapter implements ApplyCouponUseCase {


    @Override
    public void applyCoupon(Money beforePrice, Money appliedPrice, Long couponId) {

    }
}
