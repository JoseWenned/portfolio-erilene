package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.CriarUsuarioInput;
import br.com.erline.portfolio.application.dto.UsuarioOutput;
import br.com.erline.portfolio.application.security.SenhaHasher;
import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.domain.exception.UsuarioAlreadyExistsException;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CriarUsuarioUseCaseTest {

    private final UsuarioRepository usuarioRepository = mock(
            UsuarioRepository.class
    );
    private final SenhaHasher senhaHasher = mock(SenhaHasher.class);
    private final CriarUsuarioUseCase useCase = new CriarUsuarioUseCase(
            usuarioRepository,
            senhaHasher
    );

    @Test
    void deveCriarUsuarioComEmailNormalizadoESenhaHash() {
        Usuario salvo = new Usuario(
                "admin@example.com",
                "hash-gerado",
                Role.ADMIN
        );
        when(usuarioRepository.findByEmail("admin@example.com"))
                .thenReturn(Optional.empty());
        when(senhaHasher.hash("senha-secreta"))
                .thenReturn("hash-gerado");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(salvo);

        UsuarioOutput output = useCase.execute(new CriarUsuarioInput(
                " ADMIN@EXAMPLE.COM ",
                "senha-secreta",
                Role.ADMIN
        ));

        assertThat(output.email()).isEqualTo("admin@example.com");
        assertThat(output.role()).isEqualTo(Role.ADMIN);
        verify(senhaHasher).hash("senha-secreta");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void naoDeveCriarUsuarioComEmailDuplicado() {
        Usuario existente = new Usuario(
                "admin@example.com",
                "hash-existente",
                Role.ADMIN
        );
        when(usuarioRepository.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(existente));

        assertThatThrownBy(() -> useCase.execute(new CriarUsuarioInput(
                "ADMIN@EXAMPLE.COM",
                "senha-secreta",
                Role.ADMIN
        )))
                .isInstanceOf(UsuarioAlreadyExistsException.class)
                .hasMessage("Já existe um usuário cadastrado para o email: admin@example.com");

        verify(senhaHasher, never()).hash(any(String.class));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
