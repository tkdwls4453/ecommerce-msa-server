package com.msa.entry.repository;

import com.msa.entry.entity.TimeDeal;
import com.msa.entry.entity.TimeDealStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeDealRepository extends JpaRepository<TimeDeal, Long> {
    List<TimeDeal> findByTimeDealStatusAndStartTimeLessThanEqual(TimeDealStatus status, LocalDateTime dateTime);

    List<TimeDeal> findByTimeDealStatusAndEndTimeLessThanEqual(TimeDealStatus status, LocalDateTime dateTime);
}
