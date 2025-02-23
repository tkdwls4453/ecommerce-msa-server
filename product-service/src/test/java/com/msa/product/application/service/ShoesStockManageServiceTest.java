package com.msa.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.product.adapter.in.web.dto.DecreaseStockRequest;
import com.msa.product.adapter.in.web.dto.RollbackStockRequest;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.port.in.RollbackStockCommand;
import com.msa.product.application.port.out.ShoesQueryPort;
import com.msa.product.application.port.out.ShoesStockManagePort;
import com.msa.product.domain.ProductFixtures;
import com.msa.product.domain.Shoes;
import com.msa.product.exception.InsufficientStockException;
import com.msa.product.exception.NotFoundShoesException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoesStockManageServiceTest {

    @InjectMocks
    private ShoesStockManageService sut;

    @Mock
    private ShoesQueryPort shoesQueryPort;

    @Mock
    private ShoesStockManagePort shoesStockManagePort;

    /**
     * 로직
     * 주문 상품 정보 조회 -> 없는 정보면 예외 ShoesQueryPort
     * 상품 재고가 충분한 지 체크 -> 부족하면 예외
     * 상품 재고 감소 -> ShoesStockManagePort
     */
    @Nested
    @DisplayName("[SERVICE] 재고 감소 테스트")
    class DecreaseStock{

        OrderItem orderItem1 = OrderItem.builder()
            .itemId(1L)
            .quantity(2)
            .build();

        OrderItem orderItem2 = OrderItem.builder()
            .itemId(2L)
            .quantity(1)
            .build();

        DecreaseStockRequest request = DecreaseStockRequest.builder()
            .orderLine(Arrays.asList(orderItem1, orderItem2))
            .build();

        @Test
        @DisplayName("주문 상품 정보로 재고를 확인 후 감소시킨다.")
        void test2000(){
            // Given
            DecreaseStockCommand command = DecreaseStockCommand.from(request);

            List<Long> idList = command.orderLine().stream()
                .map(OrderItem::itemId)
                .toList();

            List<Shoes> shoesList = ProductFixtures.shoesList();

            when(shoesQueryPort.findByShoesIdInWithPessimisticLock(idList)).thenReturn(shoesList);

            // When
            sut.decreaseStock(command);

            // Then
            verify(shoesQueryPort, times(1)).findByShoesIdInWithPessimisticLock(idList);
            verify(shoesStockManagePort, times(1)).updateStock(anyList());
        }

        @Test
        @DisplayName("존재하지 않는 상품으로 재고 감소 요청시 예외를 반환한다.")
        void test1(){
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            OrderItem orderItem3 = OrderItem.builder()
                .itemId(3L)
                .quantity(1)
                .build();

            DecreaseStockRequest request = DecreaseStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2, orderItem3))
                .build();

            DecreaseStockCommand command = DecreaseStockCommand.from(request);

            List<Long> idList = command.orderLine().stream()
                .map(OrderItem::itemId)
                .toList();

            List<Shoes> shoesList = ProductFixtures.shoesList();

            when(shoesQueryPort.findByShoesIdInWithPessimisticLock(idList)).thenReturn(shoesList);

            // When Then
            assertThatThrownBy(() -> sut.decreaseStock(command)).isInstanceOf(NotFoundShoesException.class);

            verify(shoesQueryPort, times(1)).findByShoesIdInWithPessimisticLock(idList);
            verify(shoesStockManagePort, times(0)).updateStock(anyList());
        }

        @Test
        @DisplayName("주문 상품 정보로 재고 확인 결과 재고가 부족하면 예외를 반환한다.")
        void test2(){
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(100)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            DecreaseStockRequest request = DecreaseStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2))
                .build();

            DecreaseStockCommand command = DecreaseStockCommand.from(request);

            List<Long> idList = command.orderLine().stream()
                .map(OrderItem::itemId)
                .toList();

            List<Shoes> shoesList = ProductFixtures.shoesList();

            when(shoesQueryPort.findByShoesIdInWithPessimisticLock(idList)).thenReturn(shoesList);

            // When
            assertThatThrownBy(()-> sut.decreaseStock(command)).isInstanceOf(InsufficientStockException.class);
            verify(shoesQueryPort, times(1)).findByShoesIdInWithPessimisticLock(idList);
        }
    }

    @Nested
    @DisplayName("[SERVICE] 재고 복구 테스트")
    class RollbackStock{

        OrderItem orderItem1 = OrderItem.builder()
            .itemId(1L)
            .quantity(2)
            .build();

        OrderItem orderItem2 = OrderItem.builder()
            .itemId(2L)
            .quantity(1)
            .build();

        RollbackStockRequest request = RollbackStockRequest.builder()
            .orderLine(Arrays.asList(orderItem1, orderItem2))
            .build();

        @Test
        @DisplayName("주문 상품 정보로 재고를 확인 후 감소시킨다.")
        void test2000(){
            // Given
            RollbackStockCommand command = RollbackStockCommand.from(request);

            List<Long> idList = command.orderLine().stream()
                .map(OrderItem::itemId)
                .toList();

            List<Shoes> shoesList = ProductFixtures.shoesList();

            when(shoesQueryPort.findByShoesIdInWithPessimisticLock(idList)).thenReturn(shoesList);

            // When
            sut.rollbackStock(command);

            // Then
            verify(shoesQueryPort, times(1)).findByShoesIdInWithPessimisticLock(idList);
            verify(shoesStockManagePort, times(1)).updateStock(anyList());
        }

        @Test
        @DisplayName("존재하지 않는 상품으로 재고 감소 요청시 예외를 반환한다.")
        void test1(){
            // Given
            OrderItem orderItem1 = OrderItem.builder()
                .itemId(1L)
                .quantity(2)
                .build();

            OrderItem orderItem2 = OrderItem.builder()
                .itemId(2L)
                .quantity(1)
                .build();

            OrderItem orderItem3 = OrderItem.builder()
                .itemId(3L)
                .quantity(1)
                .build();

            RollbackStockRequest request = RollbackStockRequest.builder()
                .orderLine(Arrays.asList(orderItem1, orderItem2, orderItem3))
                .build();

            RollbackStockCommand command = RollbackStockCommand.from(request);

            List<Long> idList = command.orderLine().stream()
                .map(OrderItem::itemId)
                .toList();

            List<Shoes> shoesList = ProductFixtures.shoesList();

            when(shoesQueryPort.findByShoesIdInWithPessimisticLock(idList)).thenReturn(shoesList);

            // When Then
            assertThatThrownBy(() -> sut.rollbackStock(command)).isInstanceOf(NotFoundShoesException.class);

            verify(shoesQueryPort, times(1)).findByShoesIdInWithPessimisticLock(idList);
            verify(shoesStockManagePort, times(0)).updateStock(anyList());
        }
    }
}