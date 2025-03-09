package com.msa.entry.service;

import com.msa.entry.dto.TimeDealCreateRequest;
import com.msa.entry.dto.TimeDealResponse;
import com.msa.entry.dto.TimeDealUpdateRequest;
import com.msa.entry.entity.TimeDeal;
import com.msa.entry.repository.ModelRepository;
import com.msa.entry.repository.TimeDealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TimeDealService {

    private final TimeDealRepository timeDealRepository;
    private final ModelRepository modelRepository;

    public TimeDealService(TimeDealRepository timeDealRepository, ModelRepository modelRepository) {
        this.timeDealRepository = timeDealRepository;
        this.modelRepository = modelRepository;
    }

    @Transactional
    public TimeDealResponse createTimeDeal(TimeDealCreateRequest request){

        if (!modelRepository.existsById(request.getModelId())){
            throw new EntityNotFoundException("존재하지 않는 모델");
        }
        TimeDeal timeDeal = TimeDeal.builder()
                .modelId(request.getModelId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .quantity(request.getQuantity())
                .build();

        TimeDeal saveTimeDeal = timeDealRepository.save(timeDeal);

        return TimeDealResponse.builder()
                .timeDealId(saveTimeDeal.getTimeDealId())
                .modelId(saveTimeDeal.getModelId())
                .startTime(saveTimeDeal.getStartTime())
                .endTime(saveTimeDeal.getEndTime())
                .quantity(saveTimeDeal.getQuantity())
                .status(saveTimeDeal.getTimeDealStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public TimeDealResponse getTimeDeal(Long timeDealId){

        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 타임딜"));

        return TimeDealResponse.builder()
                .timeDealId(timeDeal.getTimeDealId())
                .modelId(timeDeal.getModelId())
                .startTime(timeDeal.getStartTime())
                .endTime(timeDeal.getEndTime())
                .quantity(timeDeal.getQuantity())
                .status(timeDeal.getTimeDealStatus())
                .build();
    }

    @Transactional
    public TimeDealResponse updateTimeDeal(Long timeDealId, TimeDealUpdateRequest request){

        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(()->new EntityNotFoundException("없는 응모"));

        timeDeal.update(request.getStartTime(),request.getEndTime(), request.getQuantity());

        return TimeDealResponse.builder()
                .timeDealId(timeDeal.getTimeDealId())
                .modelId(timeDeal.getModelId())
                .startTime(timeDeal.getStartTime())
                .endTime(timeDeal.getEndTime())
                .quantity(timeDeal.getQuantity())
                .status(timeDeal.getTimeDealStatus())
                .build();
    }

    @Transactional
    public void deleteTimeDeal(Long timeDealId){

        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(()-> new EntityNotFoundException("없는 응모"));

        timeDealRepository.delete(timeDeal);
    }

    @Transactional
    public TimeDealResponse startTimeDeal(Long timeDealId){

        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(()-> new EntityNotFoundException("없는 응모"));

        timeDeal.start();

        return TimeDealResponse.builder()
                .timeDealId(timeDeal.getTimeDealId())
                .modelId(timeDeal.getModelId())
                .startTime(timeDeal.getStartTime())
                .endTime(timeDeal.getEndTime())
                .quantity(timeDeal.getQuantity())
                .status(timeDeal.getTimeDealStatus())
                .build();
    }

    @Transactional
    public TimeDealResponse endTimeDeal(Long timeDealId){

        TimeDeal timeDeal = timeDealRepository.findById(timeDealId)
                .orElseThrow(()-> new EntityNotFoundException("없는 응모"));

        timeDeal.end();

        return TimeDealResponse.builder()
                .timeDealId(timeDeal.getTimeDealId())
                .modelId(timeDeal.getModelId())
                .startTime(timeDeal.getStartTime())
                .endTime(timeDeal.getEndTime())
                .quantity(timeDeal.getQuantity())
                .status(timeDeal.getTimeDealStatus())
                .build();
    }
}
