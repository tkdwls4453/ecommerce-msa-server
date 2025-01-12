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
    private final ObjectMapper objectMapper;

    public HashRedisTemplate(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.hashOps = redisTemplate.opsForHash();
        this.objectMapper = objectMapper;
    }

    public <T> void putAll(String key, T value) {
        try {
            Map<String, Object> map = objectMapper.convertValue(value,
                    new TypeReference<Map<String, Object>>() {});

            Map<String, String> jsonMap = map.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> {
                                try {
                                    return objectMapper.writeValueAsString(e.getValue());
                                } catch (JsonProcessingException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                    ));

            hashOps.putAll(key, jsonMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void put(String key, String field, T value) {
        try{
            String jsonValue = objectMapper.writeValueAsString(value);
            hashOps.put(key, field, jsonValue);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void putIfAbsent(String key, String field, T value) {
        try{
            String jsonValue = objectMapper.writeValueAsString(value);
            Boolean result = hashOps.putIfAbsent(key, field, jsonValue);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<T> get(String key, String field, Class<T> clazz) {
        Object value = hashOps.get(key, field);
        if (value == null) {
            return Optional.empty();
        }

        try {
            if (value instanceof String) {
                return Optional.of(objectMapper.readValue((String) value, clazz));
            }
            return Optional.of(objectMapper.convertValue(value, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                    Object fieldValue = objectMapper.readValue(jsonValue, field.getType());
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
        return hashOps.increment(key, field, 1);
    }

    public <T> Map<String, T> entries(String key, Class<T> clazz) {
        Map<String, Object> entries = hashOps.entries(key);
        Map<String, T> result = new HashMap<>();

        entries.forEach((field, value) -> {
            try {
                if (value instanceof String) {
                    try {
                        result.put(field, objectMapper.readValue((String) value, clazz));
                    } catch (JsonProcessingException e) {
                        if (clazz == String.class) {
                            @SuppressWarnings("unchecked")
                            T stringValue = (T) value;
                            result.put(field, stringValue);
                        } else {
                            result.put(field, objectMapper.convertValue(value, clazz));
                        }
                    }
                } else {
                    result.put(field, objectMapper.convertValue(value, clazz));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        return result;
    }

    public Set<String> keys(String key) {
        return hashOps.keys(key);
    }

    public <T> List<T> values(String key, Class<T> clazz) {
        List<Object> values = hashOps.values(key);
        return values.stream()
                .map(value -> {
                    if (value instanceof String) {
                        try {
                            return objectMapper.readValue((String) value, clazz);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return objectMapper.convertValue(value, clazz);
                })
                .collect(Collectors.toList());
    }

    public Long size(String key) {
        return hashOps.size(key);
    }

}
