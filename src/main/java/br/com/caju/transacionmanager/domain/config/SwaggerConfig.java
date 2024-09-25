package br.com.caju.transacionmanager.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("br.com.caju.transacionmanager")
                .build();

    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server()
                        .url("http://localhost:8080")
                        .description("Server URL in Development")))
                .info(new Info().title("Manager Transaction API")
                        .description("Manager Transaction in Caju")
                        .version("v0.0.1")
                );
    }
}
