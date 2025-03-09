package com.msa.entry.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TimeDealUpdateRequest {

//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
