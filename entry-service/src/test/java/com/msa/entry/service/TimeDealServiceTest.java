package com.msa.entry.service;

import com.msa.entry.entity.TimeDeal;
import com.msa.entry.repository.TimeDealRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TimeDealServiceTest {
    @InjectMocks
    private TimeDealService timeDealService;

    @Mock
    private TimeDealRepository timeDealRepository;

    @Test
    @DisplayName("타임딜을 생성할 수 있다")
    void createTimeDeal() {
        // given
        TimeDealCreateRequest request = new TimeDealCreateRequest(1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                100);

        // when
        TimeDealResponse response = timeDealService.createTimeDeal(request);

        // then
        assertThat(response).isNotNull();
        verify(timeDealRepository).save(any(TimeDeal.class));
    }
}
