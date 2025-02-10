package com.msa.entry.service;

import com.msa.entry.repository.EntryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

public class EntryServiceTest {

    @InjectMocks
    private EntryServcie entryServcie;

    @Mock
    private EntryRepository entryRepository;

    @DisplayName("타임딜 생성")
    @Test
    void createentry(){
        //given


        //when


        //then
    }
}
