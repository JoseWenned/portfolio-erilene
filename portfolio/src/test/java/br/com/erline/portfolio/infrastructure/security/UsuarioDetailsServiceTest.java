package br.com.erline.portfolio.infrastructure.security;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioDetailsServiceTest {

    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final UsuarioDetailsService service =
            new UsuarioDetailsService(usuarioRepository);

    @Test
    void deveCarregarUsuarioNormalizandoEmailERole() {
        Usuario usuario = new Usuario(
                "admin@example.com",
                "hash-da-senha",
                Role.ADMIN
        );
        when(usuarioRepository.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(usuario));

        UserDetails userDetails = service.loadUserByUsername(
                " ADMIN@EXAMPLE.COM "
        );

        assertThat(userDetails.getUsername()).isEqualTo("admin@example.com");
        assertThat(userDetails.getPassword()).isEqualTo("hash-da-senha");
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_ADMIN");
        verify(usuarioRepository).findByEmail("admin@example.com");
    }

    @Test
    void deveFalharQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findByEmail("inexistente@example.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername(
                "inexistente@example.com"
        ))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Usuário não encontrado.");
    }
}
