package com.uzima;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "Uzima Hospital API",
                version = "1.0",
                description = "API's for Uzima Hospital System. Testing develop",
                contact = @Contact(name = "Uzima Hospital Development Team")
        ))

public class UzimaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UzimaApplication.class, args);
    }

}
