package br.com.erline.portfolio.application.security;

public interface SenhaHasher {
    String hash(String senha);
}
