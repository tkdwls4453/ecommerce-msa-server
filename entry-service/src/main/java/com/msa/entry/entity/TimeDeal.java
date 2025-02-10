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
    private Long productId;
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
    public TimeDeal(Long productId, LocalDateTime startTime,LocalDateTime endTime, int quantity){
        this.productId = productId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
        this.timeDealStatus = TimeDealStatus.SCHEDULED;
    }

    protected TimeDeal() {}
}
