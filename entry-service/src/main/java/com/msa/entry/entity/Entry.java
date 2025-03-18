package com.msa.entry.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;
    @ManyToOne(fetch = FetchType.LAZY)
    private TimeDeal timeDeal;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime entryTime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntryStatus entryStatus;
    @Column(nullable = false)
    private Integer entryNumber;

    public Entry(){}

    @Builder
    private Entry(TimeDeal timeDeal, Long userId, LocalDateTime entryTime, Integer entryNumber) {
        this.timeDeal = timeDeal;
        this.userId = userId;
        this.entryTime = entryTime;
        this.entryNumber = entryNumber;
        this.entryStatus = EntryStatus.PENDING;
    }

    public void setEntryStatus(EntryStatus entryStatus) {
        this.entryStatus = entryStatus;
    }

}
