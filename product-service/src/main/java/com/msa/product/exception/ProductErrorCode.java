package com.msa.product.exception;

import com.msa.common.response.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements StatusCode {
    OUT_OF_STOCK_ERROR(HttpStatus.BAD_REQUEST, "FPR400", "상품의 재고가 부족합니다."),
    NOT_FOUND_SHOES_ERROR(HttpStatus.BAD_REQUEST, "FPR401", "존재하지 않는 신발입니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
