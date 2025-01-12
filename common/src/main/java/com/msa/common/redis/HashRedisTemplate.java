package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class HashRedisTemplate {

    private final HashOperations<String, String, Object> hashOps;
    private final ObjectMapper mapper;

    public HashRedisTemplate(RedisTemplate<String, String> redisTemplate, ObjectMapper mapper) {
        this.hashOps = redisTemplate.opsForHash();
        this.mapper = mapper;
    }

    public <T> void putAll(String key, T value) {
        try {
            Map<String, Object> map = mapper.convertValue(value,
                    new TypeReference<Map<String, Object>>() {});

            Map<String, String> jsonMap = map.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> valueToString(e.getValue())
                    ));

            hashOps.putAll(key, jsonMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void put(String key, String field, T value) {
        String jsonValue = valueToString(value);
        hashOps.put(key, field, jsonValue);
    }

    public <T> void putIfAbsent(String key, String field, T value) {
        String jsonValue = valueToString(value);
        hashOps.putIfAbsent(key, field, jsonValue);
    }

    public <T> Optional<T> get(String key, String field, Class<T> clazz) {
        Object value = hashOps.get(key, field);
        return Optional.ofNullable(stringToValue((String) value, clazz));
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        Map<String, Object> entries = hashOps.entries(key);
        if (entries.isEmpty()) {
            return Optional.empty();
        }

        try {
            Map<String, String> stringEntries = entries.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> (String) e.getValue()
                    ));

            T instance = clazz.getDeclaredConstructor().newInstance();
            stringEntries.forEach((fieldName, jsonValue) -> {
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object fieldValue = mapper.readValue(jsonValue, field.getType());
                    field.set(instance, fieldValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            return Optional.of(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long delete(String key, String... fields) {
        return hashOps.delete(key, (Object[]) fields);
    }

    public boolean hasKey(String key, String field) {
        return hashOps.hasKey(key, field);
    }

    public Long increment(String key, String field, long amount) {
        return hashOps.increment(key, field, amount);
    }

    public Long increment(String key, String field) {
        return increment(key, field, 1);
    }

    public <T> Map<String, T> entries(String key, Class<T> clazz) {
        Map<String, Object> entries = hashOps.entries(key);
        Map<String, T> result = new HashMap<>();
        entries.forEach((field, value) -> result.put(field, stringToValue((String) value, clazz)));
        return result;
    }

    public Set<String> keys(String key) {
        return hashOps.keys(key);
    }

    public <T> List<T> values(String key, Class<T> clazz) {
        List<Object> values = hashOps.values(key);
        return values.stream()
                .map(value -> stringToValue((String) value, clazz))
                .collect(Collectors.toList());
    }

    public Long size(String key) {
        return hashOps.size(key);
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
