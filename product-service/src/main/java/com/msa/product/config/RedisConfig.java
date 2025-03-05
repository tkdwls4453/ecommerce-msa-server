package com.msa.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();

        redisSentinelConfiguration.setMaster("mymaster");
        redisSentinelConfiguration.sentinel("redis-sentinel", 26379);
        return redisSentinelConfiguration;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration) {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisSentinelConfiguration);

        return factory;
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        return template;
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory("redis", 6379);
//    }
//
//    @Bean
//    public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Integer> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
//        return template;
//    }
}
