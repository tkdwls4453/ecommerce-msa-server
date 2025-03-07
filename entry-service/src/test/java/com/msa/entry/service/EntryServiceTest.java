package com.msa.entry.service;

import com.msa.entry.entity.Entry;
import com.msa.entry.entity.EntryStatus;
import com.msa.entry.entity.TimeDeal;
import com.msa.entry.repository.EntryRepository;
import com.msa.entry.repository.TimeDealRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EntryServiceTest {
    @InjectMocks
    private EntryService entryService;

    @Mock
    private EntryRepository entryRepository;

    @Mock
    private TimeDealRepository timeDealRepository;

    @Test
    @DisplayName("응모 신청 성공")
    void applyEntry() {
        // given
        Long timeDealId = 1L;
        Long userId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().plusHours(1))
                .quantity(100)
                .build();
        timeDeal.start();

        Entry entry = Entry.builder()
                .timeDeal(timeDeal)
                .userId(userId)
                .entryTime(LocalDateTime.now())
                .entryNumber(1)
                .build();

        when(timeDealRepository.findById(timeDealId)).thenReturn(Optional.of(timeDeal));
        when(entryRepository.save(any(Entry.class))).thenReturn(entry);

        // when
        EntryResponse response = entryService.applyEntry(timeDealId, userId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getEntryStatus()).isEqualTo(EntryStatus.PENDING);
        verify(entryRepository).save(any(Entry.class));
    }

    @Test
    @DisplayName("중복 응모 실패")
    void applyEntryDuplicate() {
        // given
        Long timeDealId = 1L;
        Long userId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().plusHours(1))
                .quantity(100)
                .build();
        timeDeal.start();

        when(timeDealRepository.findById(timeDealId)).thenReturn(Optional.of(timeDeal));
        when(entryRepository.existsByTimeDealAndUserId(timeDeal, userId)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> entryService.applyEntry(timeDealId, userId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 응모한 타임딜입니다");
    }

    @Test
    @DisplayName("당첨자 선정 성공")
    void selectWinners() {
        // given
        Long timeDealId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now().minusHours(2))
                .endTime(LocalDateTime.now().minusHours(1))
                .quantity(2)  // 당첨자 2명
                .build();
        timeDeal.start();
        timeDeal.end();

        List<Entry> entries = List.of(
                createEntry(timeDeal, 1L, 1),
                createEntry(timeDeal, 2L, 2),
                createEntry(timeDeal, 3L, 3)
        );

        when(timeDealRepository.findById(timeDealId)).thenReturn(Optional.of(timeDeal));
        when(entryRepository.findByTimeDeal(timeDeal)).thenReturn(entries);

        // when
        List<EntryResponse> winners = entryService.selectWinners(timeDealId);

        // then
        assertThat(winners).hasSize(2);  // 당첨자 수 확인
        assertThat(winners).allMatch(w -> w.getEntryStatus().equals(EntryStatus.WIN));
        verify(entryRepository).saveAll(anyList());
    }

    private Entry createEntry(TimeDeal timeDeal, Long userId, int entryNumber) {
        return Entry.builder()
                .timeDeal(timeDeal)
                .userId(userId)
                .entryTime(LocalDateTime.now())
                .entryNumber(entryNumber)
                .build();
    }
}
