package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public class JsonValueRedisTemplate<T> {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> clazz;

    public JsonValueRedisTemplate(
            RedisTemplate <String, String> redisTemplate,
            Class<T> clazz
    ) {
        this.redisTemplate = redisTemplate;
        this.clazz = clazz;
    }

    public void set(String key, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, stringValue);
    }

    public Optional<T> get(String key) throws JsonProcessingException {
        String stringValue = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(mapper.readValue(stringValue, clazz));
    }
}
