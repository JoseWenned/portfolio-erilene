package br.com.erline.portfolio.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
        ResourceHandlerRegistry registry
    ) {

        String uploadLocation =
            Paths.get(
                "uploads",
                "depoimentos"
            )
            .toAbsolutePath()
            .normalize()
            .toUri()
            .toString();

        registry
            .addResourceHandler(
                "/uploads/depoimentos/**"
            )
            .addResourceLocations(
                uploadLocation
            );
    }
}