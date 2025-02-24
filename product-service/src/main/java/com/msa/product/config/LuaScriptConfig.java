package com.msa.product.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class LuaScriptConfig {

    @Bean
    public RedisScript<Long> decreaseStockScript() throws IOException {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        ClassPathResource scriptResource = new ClassPathResource("scripts/decreaseStock.lua");

        // Lua 스크립트 파일 로드 확인
        try{
            String scriptText = Files.readString(Path.of(scriptResource.getURI()));
            redisScript.setScriptText(scriptText);
        } catch (IOException e) {
            throw new RuntimeException("루아 스크립트 로드에 실패했습니다.");
        }

        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
