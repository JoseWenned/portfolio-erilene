package br.com.erline.portfolio.domain.entity;

import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UsuarioTest {

    @Test
    void deveCriarUsuarioValido() {
        Usuario usuario = new Usuario(
                "  ADMIN@EXAMPLE.COM ",
                "hash-da-senha",
                Role.ADMIN
        );

        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getEmail()).isEqualTo("admin@example.com");
        assertThat(usuario.getSenhaHash()).isEqualTo("hash-da-senha");
        assertThat(usuario.getRole()).isEqualTo(Role.ADMIN);
        assertThat(usuario.getCreatedAt()).isNotNull();
        assertThat(usuario.getUpdatedAt()).isNotNull();
    }

    @Test
    void naoDevePermitirEmailVazio() {
        assertThatThrownBy(() ->
                new Usuario("", "hash-da-senha", Role.ADMIN)
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O email é obrigatório.");
    }

    @Test
    void naoDevePermitirSenhaHashVazia() {
        assertThatThrownBy(() ->
                new Usuario("admin@example.com", "", Role.ADMIN)
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O hash da senha é obrigatório.");
    }

    @Test
    void naoDevePermitirRoleNula() {
        assertThatThrownBy(() ->
                new Usuario("admin@example.com", "hash-da-senha", null)
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("A role é obrigatória.");
    }
}
