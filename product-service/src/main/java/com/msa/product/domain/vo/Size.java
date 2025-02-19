package com.msa.product.domain.vo;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Size {

    SIZE_220(220),
    SIZE_230(230),
    SIZE_240(240),
    SIZE_250(250),
    SIZE_260(260),
    SIZE_270(270),
    SIZE_280(280),
    SIZE_290(290);

    private final int size;

    Size(int size) {
        this.size = size;
    }

    // int 값을 ShoeSize enum으로 변환하기 위한 매핑
    private static final Map<Integer, Size> SIZE_MAP = new HashMap<>();

    static {
        for (Size size : Size.values()) {
            SIZE_MAP.put(size.getSize(), size);
        }
    }

    /**
     * 주어진 정수값에 해당하는 ShoeSize enum을 반환합니다.
     * 만약 존재하지 않는 사이즈라면 null을 반환합니다.
     */
    public static Size fromInt(int value) {
        Size size = SIZE_MAP.get(value);
        if (size == null) {
            throw new IllegalArgumentException("존재하지 않는 신발 사이즈입니다.");
        }
        return size;
    }
}
