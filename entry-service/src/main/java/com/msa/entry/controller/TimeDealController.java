package com.msa.entry.controller;

import com.msa.entry.dto.TimeDealCreateRequest;
import com.msa.entry.dto.TimeDealResponse;
import com.msa.entry.dto.TimeDealUpdateRequest;
import com.msa.entry.service.TimeDealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entry")
public class TimeDealController {

    private final TimeDealService timeDealService;

    public TimeDealController(TimeDealService timeDealService) {
        this.timeDealService = timeDealService;
    }

    @PostMapping("/create")
    public ResponseEntity<TimeDealResponse> createTimeDeal(@RequestBody TimeDealCreateRequest request) {
        return ResponseEntity.ok(timeDealService.createTimeDeal(request));
    }

    @GetMapping("/{timeDealId}")
    public ResponseEntity<TimeDealResponse> getTimeDeal(@PathVariable("timeDealId") Long timeDealId){
        return ResponseEntity.ok(timeDealService.getTimeDeal(timeDealId));
    }

    @PutMapping("/{timeDealId}")
    public ResponseEntity<TimeDealResponse> updateTimeDeal(@PathVariable("timeDealId") Long timeDealId, @RequestBody TimeDealUpdateRequest request){

        return ResponseEntity.ok(timeDealService.updateTimeDeal(timeDealId,request));
    }

    @DeleteMapping("/{timeDealId}")
    public ResponseEntity<Void> deleteTimeDeal(@PathVariable("timeDealId") Long timeDealId){
        timeDealService.deleteTimeDeal(timeDealId);

        return ResponseEntity.ok().build();
    }

}
