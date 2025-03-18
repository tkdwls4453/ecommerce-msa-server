package com.msa.entry.dto;

import com.msa.entry.entity.Entry;
import com.msa.entry.entity.EntryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class EntryResponse {
    private Long entryId;
    private Long userId;
    private Long timeDealId;
    private LocalDateTime entryTime;
    private int entryNumber;
    private EntryStatus entryStatus;

    @Builder
    public EntryResponse(Long entryId, Long userId, Long timeDealId, LocalDateTime entryTime, int entryNumber, EntryStatus entryStatus){
        this.entryId = entryId;
        this.userId = userId;
        this.timeDealId = timeDealId;
        this.entryTime = entryTime;
        this.entryNumber = entryNumber;
        this.entryStatus = entryStatus;
    }

    private EntryResponse(){}

    // static from 메서드 추가
    public static EntryResponse from(Entry entry) {
        return EntryResponse.builder()
                .entryId(entry.getEntryId())
                .userId(entry.getUserId())
                .timeDealId(entry.getTimeDeal().getTimeDealId())
                .entryTime(entry.getEntryTime())
                .entryNumber(entry.getEntryNumber())
                .entryStatus(entry.getEntryStatus())
                .build();
    }
}
