package com.msa.entry.service;

import com.msa.entry.entity.TimeDeal;
import com.msa.entry.entity.TimeDealStatus;
import com.msa.entry.repository.TimeDealRepository;
import com.msa.entry.service.TimeDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeDealScheduler {
    private final TimeDealRepository timeDealRepository;

    @Scheduled(fixedRate = 60000)
    @Async
    public void checkAndStartTimeDeals() {
        LocalDateTime now = LocalDateTime.now();
        List<TimeDeal> scheduledDeals = timeDealRepository
                .findByTimeDealStatusAndStartTimeLessThanEqual(TimeDealStatus.SCHEDULED, now);

        scheduledDeals.forEach(TimeDeal::start);
    }

    @Scheduled(fixedRate = 60000)
    @Async
    public void checkAndEndTimeDeals() {
        LocalDateTime now = LocalDateTime.now();
        List<TimeDeal> activeDeals = timeDealRepository
                .findByTimeDealStatusAndEndTimeLessThanEqual(TimeDealStatus.ACTIVE, now);

        activeDeals.forEach(TimeDeal::end);
    }
}
