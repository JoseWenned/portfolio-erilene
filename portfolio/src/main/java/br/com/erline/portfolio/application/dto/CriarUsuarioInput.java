package br.com.erline.portfolio.application.dto;

import br.com.erline.portfolio.domain.enums.Role;

public record CriarUsuarioInput(
        String email,
        String senha,
        Role role
) {
}
