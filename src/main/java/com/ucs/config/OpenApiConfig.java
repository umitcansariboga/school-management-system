package com.ucs.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "School Management System API",
                version = "1.0.0",
                description = "RESTful API documentation for the School Management System"
        ),
        security = @SecurityRequirement(name = "Bearer")
)
@SecurityScheme(
        name = "Bearer",
        type= SecuritySchemeType.HTTP,
        scheme = "Bearer",
        bearerFormat = "JWT",
        description = "Please paste your JWT token here.No bearer prefix required"
)
public class OpenApiConfig {


}
