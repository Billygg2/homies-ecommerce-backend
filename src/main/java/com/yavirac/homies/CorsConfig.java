package com.yavirac.homies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica la configuración a todos los endpoints
                        .allowedOrigins("http://localhost:4200") // Permite el acceso desde este origen
                        .allowedMethods("GET", "POST", "PATCH", "DELETE") // Permite estos métodos HTTP
                        .allowedHeaders("Content-Type", "Authorization") // Permite estos encabezados
                        .maxAge(3600); // Cached durante 1 hora
            }
        };
    }
}