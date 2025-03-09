package com.msa.entry.controller;

import com.msa.entry.dto.EntryRequest;
import com.msa.entry.dto.EntryResponse;
import com.msa.entry.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    public ResponseEntity<EntryResponse> applyEntry(@RequestBody EntryRequest request) {
        EntryResponse response = entryService.applyEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/winners/{timeDealId}")
    public ResponseEntity<List<EntryResponse>> selectWinners(@PathVariable Long timeDealId) {
        List<EntryResponse> winners = entryService.selectWinners(timeDealId);
        return ResponseEntity.ok(winners);
    }
}
