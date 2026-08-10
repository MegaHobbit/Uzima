package com.uzima.swaggerconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8600}")
    private String serverPort;

    @Bean
    public OpenAPI cgOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Uzima Hospital -  System API")
                        .description("Comprehensive API for Uzima")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Uzima Development Team")
                                .url("https://uzima.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.uzima.com")
                                .description("Production Server")));
    }

}