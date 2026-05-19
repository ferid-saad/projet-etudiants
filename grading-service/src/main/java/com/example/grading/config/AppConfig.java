package com.example.grading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("grading-service")
                .pathsToMatch("/api/**")
                .build();
    }
}
