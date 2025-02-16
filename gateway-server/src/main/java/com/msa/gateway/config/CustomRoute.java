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
            .route(p -> p.path("/users/**")
                .uri("lb://user-service"))
            .route(p -> p.path("/auth/**")
                .uri("lb://user-service"))
            .route(p -> p.path("/order/**")
                .uri("lb://order-service"))
            .build();
    }
}
