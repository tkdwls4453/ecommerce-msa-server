package com.msa.entry.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.msa.entry.entity.TimeDealStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TimeDealResponse {
    private Long timeDealId;
    private Long modelId;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;
    private int quantity;
    private TimeDealStatus status;


    @Builder
    public TimeDealResponse(Long timeDealId, Long modelId, LocalDateTime startTime,
                            LocalDateTime endTime, int quantity, TimeDealStatus status) {
        this.timeDealId = timeDealId;
        this.modelId = modelId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
        this.status = status;
    }

    private TimeDealResponse() {}

}
