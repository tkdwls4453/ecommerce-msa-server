package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public class JsonValueRedisTemplate {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonValueRedisTemplate(RedisTemplate <String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void set(String key, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, stringValue);
    }

    public <T> Optional<T> get(String key, Class<T> clazz) throws JsonProcessingException {
        String stringValue = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(mapper.readValue(stringValue, clazz));
    }
}
