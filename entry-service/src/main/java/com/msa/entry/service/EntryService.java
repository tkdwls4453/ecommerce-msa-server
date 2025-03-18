package com.msa.entry.service;

import com.msa.entry.dto.EntryRequest;
import com.msa.entry.dto.EntryResponse;
import com.msa.entry.entity.Entry;
import com.msa.entry.entity.EntryStatus;
import com.msa.entry.entity.TimeDeal;
import com.msa.entry.entity.TimeDealStatus;
import com.msa.entry.repository.EntryRepository;
import com.msa.entry.repository.TimeDealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final TimeDealRepository timeDealRepository;

    public EntryService(EntryRepository entryRepository, TimeDealRepository timeDealRepository) {
        this.entryRepository = entryRepository;
        this.timeDealRepository = timeDealRepository;
    }

    @Transactional
    public EntryResponse applyEntry(EntryRequest entryRequest) {
        TimeDeal timeDeal = timeDealRepository.findById(entryRequest.getTimeDealId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 타임딜"));

        if (timeDeal.getTimeDealStatus() != TimeDealStatus.ACTIVE) {
            throw new IllegalStateException("진행 중인 타임딜만 응모 가능");
        }

        if (entryRepository.existsByTimeDealAndUserId(timeDeal, entryRequest.getUserId())) {
            throw new IllegalStateException("이미 응모한 타임딜입니다.");
        }

        int maxEntryNumber;

        if(entryRepository.countByTimeDeal(timeDeal) == 0) {
            maxEntryNumber = 0;
        } else {
            Entry maxEntry = entryRepository.findTopByTimeDealOrderByEntryNumberDesc(timeDeal);
            maxEntryNumber = (maxEntry != null) ? maxEntry.getEntryNumber() : 0;
        }
        int newEntryNumber = maxEntryNumber + 1;

        Entry entry = Entry.builder()
                .timeDeal(timeDeal)
                .userId(entryRequest.getUserId())
                .entryTime(LocalDateTime.now())
                .entryNumber(newEntryNumber)
                .build();

        Entry saveEntry = entryRepository.save(entry);

        return EntryResponse.builder()
                .entryId(saveEntry.getEntryId())
                .userId(saveEntry.getUserId())
                .timeDealId(saveEntry.getTimeDeal().getTimeDealId())
                .entryTime(saveEntry.getEntryTime())
                .entryNumber(saveEntry.getEntryNumber())
                .entryStatus(saveEntry.getEntryStatus())
                .build();
    }

    @Transactional
    public List<EntryResponse> selectWinners(Long timeDealId) {
        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 타임딜입니다."));

        if (timeDeal.getTimeDealStatus() != TimeDealStatus.ENDED) {
            throw new IllegalStateException("종료된 타임딜만 당첨자 선정이 가능합니다.");
        }


        List<Entry> entries = entryRepository.findByTimeDeal(timeDeal);
        int winnerCount = Math.min(timeDeal.getQuantity(), entries.size());

        List<Entry> shuffledEntries = new ArrayList<>(entries);
        Collections.shuffle(shuffledEntries);
        List<Entry> winners = shuffledEntries.subList(0, winnerCount);

        for (Entry entry : entries) {
            if (winners.contains(entry)) {
                entry.setEntryStatus(EntryStatus.WIN);
            } else {
                entry.setEntryStatus(EntryStatus.LOSE);
            }
        }
        entryRepository.saveAll(entries);

        return winners.stream()
                .map(EntryResponse::from)
                .collect(Collectors.toList());
    }

}