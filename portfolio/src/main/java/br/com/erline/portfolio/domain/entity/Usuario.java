package br.com.erline.portfolio.domain.entity;

import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.domain.exception.DomainException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {

    private UUID id;
    private String email;
    private String senhaHash;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Usuario() {
        // Construtor privado para reconstituição
    }

    public Usuario(
        String email,
        String senhaHash,
        Role role
    ) {
        validarEmail(email);
        validarSenhaHash(senhaHash);
        validarRole(role);

        this.id = UUID.randomUUID();
        this.email = email.trim().toLowerCase();
        this.senhaHash = senhaHash;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public static Usuario reconstituir(
        UUID id,
        String email,
        String senhaHash,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        Usuario usuario = new Usuario();

        usuario.id = id;
        usuario.email = email;
        usuario.senhaHash = senhaHash;
        usuario.role = role;
        usuario.createdAt = createdAt;
        usuario.updatedAt = updatedAt;

        return usuario;
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new DomainException("O email é obrigatório.");
        }
    }

    private void validarSenhaHash(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new DomainException("O hash da senha é obrigatório.");
        }
    }

    private void validarRole(Role role) {
        if (role == null) {
            throw new DomainException("A role é obrigatória.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}