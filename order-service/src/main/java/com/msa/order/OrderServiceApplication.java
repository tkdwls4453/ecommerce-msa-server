package com.msa.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.msa.common", "com.msa.order"})
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        // CI 테스트를 위한 변경점 생성
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
