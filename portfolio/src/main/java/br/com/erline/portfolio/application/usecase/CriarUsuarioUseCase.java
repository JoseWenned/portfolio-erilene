package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.CriarUsuarioInput;
import br.com.erline.portfolio.application.dto.UsuarioOutput;
import br.com.erline.portfolio.application.security.SenhaHasher;
import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.exception.UsuarioAlreadyExistsException;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;

public class CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaHasher senhaHasher;

    public CriarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            SenhaHasher senhaHasher
    ) {
        this.usuarioRepository = usuarioRepository;
        this.senhaHasher = senhaHasher;
    }

    public UsuarioOutput execute(CriarUsuarioInput input) {
        String email = input.email().trim().toLowerCase();

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new UsuarioAlreadyExistsException(email);
        }

        Usuario usuario = new Usuario(
                email,
                senhaHasher.hash(input.senha()),
                input.role()
        );

        return UsuarioOutput.from(usuarioRepository.save(usuario));
    }
}
