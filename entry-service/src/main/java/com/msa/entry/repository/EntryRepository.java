package com.msa.entry.repository;

import com.msa.entry.entity.Entry;
import com.msa.entry.entity.TimeDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    boolean existsByTimeDealAndUserId(TimeDeal timeDeal, Long userId);
    int countByTimeDeal(TimeDeal timeDeal);
    Integer findMaxEntryNumberByTimeDeal(TimeDeal timeDeal);
    List<Entry> findByTimeDeal(TimeDeal timeDeal);
    Entry findTopByTimeDealOrderByEntryNumberDesc(TimeDeal timeDeal);
}
