package com.msa.entry.controller;

import com.msa.common.response.ApiResponse;
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
    public ApiResponse<TimeDealResponse> createTimeDeal(@RequestBody TimeDealCreateRequest request) {
        return ApiResponse.success(timeDealService.createTimeDeal(request));
    }

    @GetMapping("/{timeDealId}")
    public ApiResponse<TimeDealResponse> getTimeDeal(@PathVariable("timeDealId") Long timeDealId){
        return ApiResponse.success(timeDealService.getTimeDeal(timeDealId));
    }

    @PutMapping("/{timeDealId}")
    public ApiResponse<TimeDealResponse> updateTimeDeal(@PathVariable("timeDealId") Long timeDealId, @RequestBody TimeDealUpdateRequest request){

        return ApiResponse.success(timeDealService.updateTimeDeal(timeDealId,request));
    }

    @DeleteMapping("/{timeDealId}")
    public ApiResponse<Void> deleteTimeDeal(@PathVariable("timeDealId") Long timeDealId){
        timeDealService.deleteTimeDeal(timeDealId);

        return ApiResponse.success();
    }

}
