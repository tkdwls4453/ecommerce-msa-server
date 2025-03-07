package com.msa.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.msa.common", "com.msa.entry"})
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class EntryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryServiceApplication.class, args);
    }

}
