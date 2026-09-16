package br.com.erline.portfolio.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/actuator/health"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/depoimentos/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/depoimentos"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/uploads/depoimentos/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/usuarios"
                        )
                        .hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/depoimentos/**"
                        )
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(basic -> basic
                        .realmName("portfolio"));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:3000",
                        "http://127.0.0.1:3000"
                )
                
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PATCH",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}