package br.com.erline.portfolio.interfaces.rest.dto.response;

import br.com.erline.portfolio.application.dto.UsuarioOutput;
import br.com.erline.portfolio.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UsuarioResponse from(UsuarioOutput output) {
        return new UsuarioResponse(
                output.id(),
                output.email(),
                output.role(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}