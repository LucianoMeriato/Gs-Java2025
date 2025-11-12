package br.com.fiap.upskill.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Upskilling/Reskilling API - FIAP GS 2025")
                .description("API RESTful para plataforma de upskilling e reskilling conectada ao Futuro do Trabalho (2030+)")
                .version("1.0.0"));
    }
}
