package com.msa.entry.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
class EntryTest {
    @Test
    @DisplayName("엔트리 생성")
    void createEntry(){
        //given
        TimeDeal timeDeal = TimeDeal.builder()
                .productId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        Long userId  = 2L;
        LocalDateTime entyrTime = LocalDateTime.now();

        //when
        Entry entry = Entry.builder()
                .timeDeal(timeDeal)
                .userId(userId)
                .entryTime(entyrTime)
                .build();

        //then
        assertThat(entry.getTimeDeal()).isEqualTo(timeDeal);
        assertThat(entry.getUserId()).isEqualTo(userId);
        assertThat(entry.getEntryTime()).isEqualTo(entyrTime);
        assertThat(entry.getEntryStatus()).isEqualTo(EntryStatus.PENDING);
    }

}