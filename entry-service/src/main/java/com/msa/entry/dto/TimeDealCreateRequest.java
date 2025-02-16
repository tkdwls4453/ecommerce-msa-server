package com.msa.entry.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeDealCreateRequest {

    private Long modelId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int quantity;

    @Builder
    public TimeDealCreateRequest(long modelId, LocalDateTime startTime, LocalDateTime endTime, int quantity) {
        this.modelId = modelId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }
    private TimeDealCreateRequest() {}

}
