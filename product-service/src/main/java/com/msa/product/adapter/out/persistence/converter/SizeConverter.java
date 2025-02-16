package com.msa.product.adapter.out.persistence.converter;

import com.msa.product.domain.vo.Size;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class SizeConverter implements AttributeConverter<Size, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Size size) {
        if (size == null) {
            return null;
        }
        return size.getSize();
    }

    @Override
    public Size convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Size.fromInt(dbData);
    }
}
