package br.com.erline.portfolio.interfaces.rest.dto.request;

import br.com.erline.portfolio.application.dto.CriarUsuarioInput;
import br.com.erline.portfolio.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email deve ser válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        String senha,

        @NotNull(message = "O perfil é obrigatório.")
        Role role
) {
    public CriarUsuarioInput toInput() {
        return new CriarUsuarioInput(email, senha, role);
    }
}