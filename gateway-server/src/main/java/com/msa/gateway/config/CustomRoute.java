package com.msa.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRoute {

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {

        return builder.routes()
            .route(p -> p.path("/users/**","/internal/users/**")
                .uri("lb://user-service"))
            .route(p -> p.path("/auth/**")
                .uri("lb://user-service"))
            .route(p -> p.path("/orders/**","/internal/orders/**")
                .uri("lb://order-service"))
            .route(p -> p.path("/payments/**", "/internal/payments/**")
                .uri("lb://payment-service"))
            .build();
    }
}
