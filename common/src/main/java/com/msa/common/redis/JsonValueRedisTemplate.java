package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class JsonValueRedisTemplate {

    private final ValueOperations<String, String> valueOps;
    private final ObjectMapper mapper;

    public JsonValueRedisTemplate(RedisTemplate <String, String> redisTemplate, ObjectMapper objectMapper) {
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
        valueOps.increment(key,amount);
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

    public <T> T getAndSet(String key, T newValue, Class<T> clazz) {
        String stringValue = valueToString(newValue);
        String oldValue = valueOps.getAndSet(key, stringValue);
        return stringToValue(oldValue,clazz);
    }

    public <T> boolean setIfAbsent(String key, T value, Class<T> clazz) {
        String stringValue = valueToString(value);
        return valueOps.setIfAbsent(key, stringValue);
    }

    public <T> boolean setIfAbsent(String key, T value, Class<T> clazz, long time, TimeUnit timeUnit) {
        String stringValue = valueToString(value);
        return valueOps.setIfAbsent(key, stringValue, time, timeUnit);
    }

    private <T> String valueToString(T value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T stringToValue(String stringValue, Class<T> clazz) {
        try {
            return mapper.readValue(stringValue, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
