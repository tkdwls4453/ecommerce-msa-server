package com.msa.entry.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TimeDealUpdateRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int quantity;

    @Builder
    public TimeDealUpdateRequest(LocalDateTime startTime, LocalDateTime endTime, int quantity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }

    private TimeDealUpdateRequest(){}


}
