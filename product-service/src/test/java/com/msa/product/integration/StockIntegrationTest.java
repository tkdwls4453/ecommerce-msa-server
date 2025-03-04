package com.msa.product.integration;

import static org.assertj.core.api.Assertions.*;

import com.msa.product.adapter.out.persistence.ShoesCommandJpaRepository;
import com.msa.product.adapter.out.persistence.ShoesEntity;
import com.msa.product.adapter.out.persistence.ShoesQueryJpaRepository;
import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.service.ShoesStockManageService;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class StockIntegrationTest {

    @Autowired
    private ShoesStockManageService shoesStockManageService;

    @Autowired
    private ShoesCommandJpaRepository shoesCommandJpaRepository;

    @Autowired
    private ShoesQueryJpaRepository shoesQueryJpaRepository;

    @AfterEach
    void tearDown() {
        shoesCommandJpaRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("신발 재고 관련 동시성 테스트")
    class StockConcurrencyTest {
        @Test
        @DisplayName("동시에 여러 재고 감소 요청이 와도 안전하게 처리한다.")
        void test2000() throws InterruptedException {
            // Given
            ShoesEntity shoesEntity = ShoesEntity.builder()
                .quantity(100)
                .build();

            // 해당 상품의 ID가 1L라고 가정
            shoesCommandJpaRepository.save(shoesEntity);

            DecreaseStockCommand command = DecreaseStockCommand.builder()
                .orderLine(
                    Collections.singletonList(
                        OrderItem.builder()
                            .itemId(1L)
                            .quantity(1)
                            .build()
                    )
                )
                .build();

            int threadCount = 100;
            ExecutorService executor = Executors.newFixedThreadPool(10);
            CountDownLatch latch = new CountDownLatch(threadCount);

            // When
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        shoesStockManageService.decreaseStock(command);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // 모든 스레드의 작업이 완료될 때까지 대기
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Timeout waiting for tasks to finish");
            }
            executor.shutdown();

            // Then
            // 서비스 로직이 별도의 트랜잭션으로 실행되므로, DB에서 최신 상태를 조회
            Optional<ShoesEntity> result = shoesQueryJpaRepository.findById(1L);
            assertThat(result.isPresent()).isTrue();
            // 100개의 감소 요청이 수행되어 재고가 0이어야 함
            assertThat(result.get().getQuantity()).isEqualTo(0);
        }
    }
}
