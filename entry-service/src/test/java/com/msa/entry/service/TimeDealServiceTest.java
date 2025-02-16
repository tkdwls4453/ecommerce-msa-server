package com.msa.entry.service;

import com.msa.entry.dto.TimeDealCreateRequest;
import com.msa.entry.dto.TimeDealResponse;
import com.msa.entry.dto.TimeDealUpdateRequest;
import com.msa.entry.entity.TimeDeal;
import com.msa.entry.entity.TimeDealStatus;
import com.msa.entry.repository.ModelRepository;
import com.msa.entry.repository.TimeDealRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class TimeDealServiceTest {
    @InjectMocks
    private TimeDealService timeDealService;

    @Mock
    private TimeDealRepository timeDealRepository;

    @Mock
    private ModelRepository modelRepository;

    @Test
    @DisplayName("타임딜 생성")
    void createTimeDeal() {
        // given
        TimeDealCreateRequest request = new TimeDealCreateRequest(1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                100);

        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(request.getModelId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .quantity(request.getQuantity())
                .build();

        when(modelRepository.existsById(any(Long.class))).thenReturn(true);  // Mock 동작 추가
        when(timeDealRepository.save(any(TimeDeal.class))).thenReturn(timeDeal);

        // when
        TimeDealResponse response = timeDealService.createTimeDeal(request);

        // then
        assertThat(response).isNotNull();
        verify(timeDealRepository).save(any(TimeDeal.class));
        verify(modelRepository).existsById(any(Long.class));
    }

    @Test
    @DisplayName("타임딜 조회")
    void getTimeDeal() {
        // given
        Long timeDealId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        when(timeDealRepository.findById(timeDealId))
                .thenReturn(Optional.of(timeDeal));

        // when
        TimeDealResponse response = timeDealService.getTimeDeal(timeDealId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getModelId()).isEqualTo(timeDeal.getModelId());
        verify(timeDealRepository).findById(timeDealId);
    }

    @Test
    @DisplayName("타임딜 수정")
    void updateTimeDeal() {
        // given
        Long timeDealId = 1L;
        TimeDealUpdateRequest request = new TimeDealUpdateRequest(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                200
        );

        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        when(timeDealRepository.findById(timeDealId))
                .thenReturn(Optional.of(timeDeal));

        // when
        TimeDealResponse response = timeDealService.updateTimeDeal(timeDealId, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getQuantity()).isEqualTo(request.getQuantity());
        verify(timeDealRepository).findById(timeDealId);
    }

    @Test
    @DisplayName("타임딜 삭제")
    void deleteTimeDeal() {
        // given
        Long timeDealId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        when(timeDealRepository.findById(timeDealId))
                .thenReturn(Optional.of(timeDeal));

        // when
        timeDealService.deleteTimeDeal(timeDealId);

        // then
        verify(timeDealRepository).findById(timeDealId);
        verify(timeDealRepository).delete(timeDeal);
    }

    @Test
    @DisplayName("예정된 타임딜 시작")
    void startTimeDeal() {
        // given
        Long timeDealId = 1L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        when(timeDealRepository.findById(timeDealId))
                .thenReturn(Optional.of(timeDeal));

        // when
        TimeDealResponse response = timeDealService.startTimeDeal(timeDealId);

        // then
        assertThat(response.getStatus()).isEqualTo(TimeDealStatus.ACTIVE);
        verify(timeDealRepository).findById(timeDealId);
    }

    @Test
    @DisplayName("타임딜 종료")
    void endTimeDeal() {
        // given
        Long timeDealId = 2L;
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .quantity(100)
                .build();

        timeDeal.start(); // SCHEDULED -> ACTIVE 상태로 변경

        when(timeDealRepository.findById(timeDealId))
                .thenReturn(Optional.of(timeDeal));

        // when
        TimeDealResponse response = timeDealService.endTimeDeal(timeDealId);

        // then
        assertThat(response.getStatus()).isEqualTo(TimeDealStatus.ENDED);
        verify(timeDealRepository).findById(timeDealId);
    }


}