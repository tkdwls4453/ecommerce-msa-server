package com.msa.common.converter;

import com.msa.common.vo.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IntToMoneyConverter implements Converter<Integer, Money> {

    @Override
    public Money convert(Integer source) {
        if (source == null) {
            throw new IllegalArgumentException("source should not be null");
        }
        return new Money(source);
    }
}
