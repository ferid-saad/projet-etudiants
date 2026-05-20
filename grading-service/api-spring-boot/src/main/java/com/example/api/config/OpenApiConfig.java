package com.example.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Micro-service Etudiants")
                        .version("2.0")
                        .description("Documentation du micro-service de gestion des etudiants et departements.")
                        .contact(new Contact()
                                .name("Ferid Saad")
                                .email("ferid@example.com")));
    }
}