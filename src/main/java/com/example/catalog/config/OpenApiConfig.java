package com.example.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class OpenApiConfig {

    private static final Logger log = LoggerFactory.getLogger(OpenApiConfig.class);

    @Bean
    public OpenAPI customOpenAPI() {
        // Intentar cargar open-spec.yml desde classpath
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream is = OpenApiConfig.class.getResourceAsStream("/open-spec.yml")) {
            if (is != null) {
                OpenAPI openAPI = mapper.readValue(is, OpenAPI.class);
                log.info("Cargado open-spec.yml desde classpath");
                return openAPI;
            }
        } catch (IOException e) {
            log.warn("Error parseando open-spec.yml desde classpath: {}", e.getMessage());
        }

        // Intentar cargar desde filesystem (ruta relativa al ejecutable/working dir)
        try {
            Path fsPath = Paths.get("open-spec.yml");
            if (Files.exists(fsPath)) {
                OpenAPI openAPI = mapper.readValue(fsPath.toFile(), OpenAPI.class);
                log.info("Cargado open-spec.yml desde filesystem: {}", fsPath.toAbsolutePath());
                return openAPI;
            }
        } catch (IOException e) {
            log.warn("Error parseando open-spec.yml desde filesystem: {}", e.getMessage());
        }

        // Fallback: configuración programática (como antes)
        io.swagger.v3.oas.models.security.SecurityScheme bearerScheme = new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        OpenAPI fallback = new OpenAPI()
                .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth", bearerScheme))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth"))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("MemoWorks API")
                        .version("0.0.1")
                        .description("Documentación OpenAPI generada por springdoc-openapi"));

        log.info("Usando configuración OpenAPI por defecto (fallback)");
        return fallback;
    }
}
