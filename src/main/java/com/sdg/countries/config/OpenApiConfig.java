package com.sdg.countries.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "API for managing population data",
            version = "1.0",
            description = "Technical test for SDG"
        ),
    servers = {
        @Server(
            description = "${springdoc.swagger-ui.server.description}",
            url = "${springdoc.swagger-ui.server.url}"
        )
    }
)
public class OpenApiConfig { }
