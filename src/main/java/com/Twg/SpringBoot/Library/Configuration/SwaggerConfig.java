package com.Twg.SpringBoot.Library.Configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Booking System")
                        .description("API documentation for the Library Booking System")
                        .version("1..0")
                        .contact(new Contact()
                                .name("Sainath")
                                .email("nathsai6309441654@gmail.com")
                        )
                )
                // Add security requirement for JWT
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))

                // Define the security scheme
                .components(new Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}

