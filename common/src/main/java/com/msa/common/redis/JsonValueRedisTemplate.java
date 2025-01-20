package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.exception.RedisParsingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JsonValueRedisTemplate {

    private final ValueOperations<String, String> valueOps;
    private final ObjectMapper mapper;

    public JsonValueRedisTemplate(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.valueOps = redisTemplate.opsForValue();
        this.mapper = objectMapper;
    }

    public <T> void set(String key, T value) {
        valueOps.set(key, valueToString(value));
    }

    public <T> void set(String key, T value, long time, TimeUnit timeUnit) {
        valueOps.set(key, valueToString(value), time, timeUnit);
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        String stringValue = valueOps.get(key);
        return Optional.ofNullable(stringToValue(stringValue, clazz));
    }

    public void increment(String key) {
        increment(key, 1);
    }

    public void increment(String key, long amount) {
        valueOps.increment(key, amount);
    }

    public void decrement(String key) {
        decrement(key, 1);
    }

    public void decrement(String key, long amount) {
        valueOps.decrement(key, amount);
    }

    public boolean delete(String key) {
        return valueOps.getOperations().delete(key);
    }

    public <T> Optional<T> getAndSet(String key, T newValue, Class<T> clazz) {
        String stringValue = valueToString(newValue);
        String oldValue = valueOps.getAndSet(key, stringValue);
        return Optional.ofNullable(stringToValue(oldValue, clazz));
    }

    public <T> boolean setIfAbsent(String key, T value) {
        String stringValue = valueToString(value);
        return valueOps.setIfAbsent(key, stringValue);
    }

    public <T> boolean setIfAbsent(String key, T value, long time, TimeUnit timeUnit) {
        String stringValue = valueToString(value);
        return valueOps.setIfAbsent(key, stringValue, time, timeUnit);
    }

    public <T> void multiSet(Map<String, T> keyValueMap) {
        valueOps.multiSet(valueToStringMap(keyValueMap));
    }

    public <T> boolean multiSetIfAbsent(Map<String, T> keyValueMap) {
        return valueOps.multiSetIfAbsent(valueToStringMap(keyValueMap));
    }

    public <T> Optional<List<T>> multiGet(Collection<String> keys, Class<T> clazz) {
        List<T> collect = valueOps.multiGet(keys).stream()
                .map(e -> stringToValue(e, clazz))
                .collect(Collectors.toList());

        return Optional.of(collect);
    }

    public Long size(String key) {
        return valueOps.size(key);
    }

    private <T> Map<String, String> valueToStringMap(Map<String, T> keyValueMap) {
        return keyValueMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> valueToString(e.getValue())
                ));
    }

    private <T> String valueToString(T value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RedisParsingException(e);
        }
    }

    private <T> T stringToValue(String stringValue, Class<T> clazz) {
        try {
            return mapper.readValue(stringValue, clazz);
        } catch (JsonProcessingException e) {
            throw new RedisParsingException(e, stringValue, clazz);
        }
    }
}
