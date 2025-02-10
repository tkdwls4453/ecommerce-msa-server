package com.msa.entry.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDealTest {
    @Test
    @DisplayName("타임딜 생성")
    void createTimeDeal(){
        //given
        Long productId = 1L;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);
        int quantity = 100;

        //when
        TimeDeal timeDeal = TimeDeal.builder()
                .productId(productId)
                .startTime(startTime)
                .endTime(endTime)
                .quantity(quantity)
                .build();

        //then
        assertThat(timeDeal.getProductId()).isEqualTo(productId);
        assertThat(timeDeal.getStartTime()).isEqualTo(startTime);
        assertThat(timeDeal.getEndTime()).isEqualTo(endTime);
        assertThat(timeDeal.getQuantity()).isEqualTo(quantity);

        assertThat(timeDeal.getTimeDealStatus()).isEqualTo(TimeDealStatus.SCHEDULED);
    }
}