package br.com.erline.portfolio.interfaces.rest.handler;

import br.com.erline.portfolio.domain.exception.UsuarioAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveRetornarConflitoQuandoUsuarioJaExiste() {
        UsuarioAlreadyExistsException exception =
                new UsuarioAlreadyExistsException("admin@example.com");

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleUsuarioAlreadyExists(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.getBody().error()).isEqualTo("Conflito");
        assertThat(response.getBody().message())
                .isEqualTo("Já existe um usuário cadastrado para o email: admin@example.com");
    }
}