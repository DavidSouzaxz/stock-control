// src/main/java/com/challenge/stock/control/config/OpenApiConfig.java
package com.challenge.stock.control.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Stock Control API")
                .version("v1")
                .description("API para controle de estoque"));
    }
}
