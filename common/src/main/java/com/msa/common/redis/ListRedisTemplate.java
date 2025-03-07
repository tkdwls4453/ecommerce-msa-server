package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.ListOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListRedisTemplate {
    private final ListOperations<String, String> op;
    private final ObjectMapper mapper;

    public ListRedisTemplate(ListOperations<String, String> op, ObjectMapper mapper) {
        this.op = op;
        this.mapper = mapper;
    }


    public <T> void addLeftToList(String key, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        op.leftPush(key,stringValue);
    }
    public <T> void addRightToList(String key, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        op.rightPush(key,stringValue);
    }

    public <T> Optional<T> popLeftFromList(String key, Class<T> clazz) throws JsonProcessingException {
        String stringValue = op.leftPop(key);
        return Optional.ofNullable(mapper.readValue(stringValue,clazz));
    }
    public <T> Optional<T> popRightFromList(String key, Class<T> clazz) throws JsonProcessingException {
        String stringValue = op.rightPop(key);
        return Optional.ofNullable(mapper.readValue(stringValue,clazz));
    }

    public <T> List<T> getAllList(String key, Class<T> clazz) throws JsonProcessingException {
        List<String> stringValues = op.range(key, 0, -1);
        List<T> resultSet = new ArrayList<>();

        if (stringValues != null) {
            for (String stringValue : stringValues) {
                T value = mapper.readValue(stringValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }
    public <T> void removeFromList(String key, T value) throws JsonProcessingException{
        String stringValue = mapper.writeValueAsString(value);
        op.remove(key,1,stringValue);
    }

    public <T> Optional<T> getIndexAt(String key, long index, Class<T> clazz) throws JsonProcessingException {
        String stringValue = op.index(key, index);
        return Optional.ofNullable(mapper.readValue(stringValue, clazz));
    }

    public <T> void updateIndexAt(String key, long index, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        op.set(key, index, stringValue);
    }

}