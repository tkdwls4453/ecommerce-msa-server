package com.msa.entry.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TimeDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeDealId;
    @Column(nullable = false)
    private Long modelId;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    private int quantity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeDealStatus timeDealStatus;
    @OneToMany(mappedBy = "timeDeal")
    private List<Entry> entries = new ArrayList<>();

    @Builder
    public TimeDeal(Long modelId, LocalDateTime startTime,LocalDateTime endTime, int quantity){
        this.modelId = modelId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
        this.timeDealStatus = TimeDealStatus.SCHEDULED;
    }

    public TimeDeal() {}

    public void update(LocalDateTime startTime, LocalDateTime endTime, int quantity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }

    public void start() {
        if (this.timeDealStatus != timeDealStatus.SCHEDULED){
            throw new IllegalStateException("이미 시작되었거나 종료된 타임딜");
        }
        this.timeDealStatus = TimeDealStatus.ACTIVE;
    }

    public void end(){
        if (this.timeDealStatus != timeDealStatus.ACTIVE){
            throw new IllegalStateException("진행중인 타임딜만 종료 가능");
        }
        this.timeDealStatus = TimeDealStatus.ENDED;
    }
}
