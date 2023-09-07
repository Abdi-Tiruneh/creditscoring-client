package com.dxvalley.creditscoring.appConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class OpenApiConfig {

    private static final String DESCRIPTION = "Coming soon. But till then, Abdi is the goat.";

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CREDIT SCORING Platform API")
                        .description(DESCRIPTION)
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Abdi Tiruneh")
                                .email("abditrnh@gmail.com")
                                .url("https://github.com/Abdi-Tiruneh")
                        )
                        .license(new License()
                                .name("CoopBank")
                                .url("https://coopbankoromia.com.et/")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/chalameg/crowdfunding-backend")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .servers(Collections.singletonList(
                        new Server()
                                .description("Local Test Server")
                ));
    }
}
