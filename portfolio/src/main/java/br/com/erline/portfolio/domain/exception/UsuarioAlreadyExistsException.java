package br.com.erline.portfolio.domain.exception;

public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException(String email) {
        super("Já existe um usuário cadastrado para o email: " + email);
    }
}
