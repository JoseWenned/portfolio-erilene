package br.com.erline.portfolio.infrastructure.config;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public CommandLineRunner adminBootstrap(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        @Value("${portfolio.security.admin.email:}") String email,
        @Value("${portfolio.security.admin.password:}") String password
    ) {
        return args -> {
            if (email.isBlank() || password.isBlank()) {
                return;
            }

            if (usuarioRepository.findByEmail(email).isPresent()) {
                return;
            }

            Usuario admin = new Usuario(
                email,
                passwordEncoder.encode(password),
                Role.ADMIN
            );

            usuarioRepository.save(admin);
        };
    }
}
