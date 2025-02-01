package com.msa.order.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService sut;

    /**
     * 주문 접수 서비스 기능
     * 입력: userId, 주문 접수 커맨드
     * 주문 초기화 (생성), 결제 금액 검증
     * 쿠폰 적용 -> 쿠폰 서비스
     * 재고 차감 -> 상품 섭비스
     * 데이터베이스에 주문 저장
     */

    @Nested
    @DisplayName("고객 아이디와 주문 접수 정보로 주문을 접수한다.")
    class newOrder{
        @Test
        @DisplayName("주문 접수시, 쿠폰 적용과 재고를 차감하고 주문을 성공적으로 생성한다.")
        void test2000(){
        }

        @Test
        @DisplayName("알맞지 않은 결제 금액으로 주문 접수시, 주문 생성에 실패하고 예외를 발생시킨다.")
        void test1(){
        }

        @Test
        @DisplayName("주문 접수시, 재고 감소에 실패하면 실패 주문을 생성하고 예외를 발생시킨다.")
        void test2(){
        }

        @Test
        @DisplayName("주문 접수시, 쿠폰 적용에 실패하면 적용된 쿠폰을 복구하고 실패 주문을 생성하고 예외를 발생시킨다.")
        void test3(){
        }

        @Test
        @DisplayName("주문 접수시, 쿠폰 적용에 실패하면 적용된 쿠폰을 복구하고 실패 주문을 생성하고 예외를 발생시킨다.")
        void test4(){
        }
    }

}