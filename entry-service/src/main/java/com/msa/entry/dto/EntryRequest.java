package com.msa.entry.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EntryRequest {
    private Long userId;
    private Long timeDealId;

    @Builder
    public EntryRequest(Long userId, Long timeDealId) {
        this.userId = userId;
        this.timeDealId = timeDealId;
    }

    private EntryRequest(){}
}