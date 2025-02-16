package com.msa.common.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.msa.common.vo.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public SimpleModule moneyModule(){
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Money.class, new MoneyDeserializer());
        module.addSerializer(Money.class, new MoneySerializer());
        return module;
    }
}
