package com.store.api.store.backend.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Store API with Spring Boot and PostgreSQL")
                    .version("1.0")
                    .description("This is Store API using Spring Boot and PostgreSQL")
            )
    }

}