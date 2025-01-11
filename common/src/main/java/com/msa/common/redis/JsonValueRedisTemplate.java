package com.msa.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
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

    public <T> void addLeftToList(String key, T vlaue) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(vlaue);
        redisTemplate.opsForList().leftPush(key,stringValue);
    }
    public <T> void addRightToList(String key, T vlaue) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(vlaue);
        redisTemplate.opsForList().rightPush(key,stringValue);
    }

    public <T> Optional<T> popLeftFromList(String key, Class<T> clazz) throws JsonProcessingException {
        String stringValue = redisTemplate.opsForList().leftPop(key);
        return Optional.ofNullable(mapper.readValue(stringValue,clazz));
    }
    public <T> Optional<T> popRightFromList(String key, Class<T> clazz) throws JsonProcessingException {
        String stringValue = redisTemplate.opsForList().rightPop(key);
        return Optional.ofNullable(mapper.readValue(stringValue,clazz));
    }

    public <T> List<T> getAllList(String key, Class<T> clazz) throws JsonProcessingException {
        List<String> stringValues = redisTemplate.opsForList().range(key, 0, -1);
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
        redisTemplate.opsForList().remove(key,1,stringValue);
    }

    public <T> Optional<T> getIndexAt(String key, long index, Class<T> clazz) throws JsonProcessingException {
        String stringValue = redisTemplate.opsForList().index(key, index);
        return Optional.ofNullable(mapper.readValue(stringValue, clazz));
    }

    public <T> void updateIndexAt(String key, long index, T value) throws JsonProcessingException {
        String stringValue = mapper.writeValueAsString(value);
        redisTemplate.opsForList().set(key, index, stringValue);
    }

}
