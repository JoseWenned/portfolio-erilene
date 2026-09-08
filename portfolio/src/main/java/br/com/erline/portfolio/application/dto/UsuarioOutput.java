package br.com.erline.portfolio.application.dto;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioOutput(
        UUID id,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UsuarioOutput from(Usuario usuario) {
        return new UsuarioOutput(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
