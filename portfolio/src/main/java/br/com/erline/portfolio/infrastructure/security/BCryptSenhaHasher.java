package br.com.erline.portfolio.infrastructure.security;

import br.com.erline.portfolio.application.security.SenhaHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptSenhaHasher implements SenhaHasher {

    private final PasswordEncoder passwordEncoder;

    public BCryptSenhaHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String senha) {
        return passwordEncoder.encode(senha);
    }
}
