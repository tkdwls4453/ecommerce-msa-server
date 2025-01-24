package com.msa.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.msa.common", "com.msa.entry"})
@SpringBootApplication
public class EntryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryServiceApplication.class, args);
    }

}
