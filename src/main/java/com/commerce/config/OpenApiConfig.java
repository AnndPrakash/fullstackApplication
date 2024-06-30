package com.commerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Ecommerce-Application", version = "v1")
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openApi = new OpenAPI();
        openApi.servers(Arrays.asList(
            new Server().url("http://localhost:8080").description("Local server"),
            new Server().url("http://localhost:8081").description("Test server"),
            new Server().url("http://dev.example.com").description("Development server"),
            new Server().url("https://api.example.com").description("Production server")
        ));
        return openApi;
    }
}
