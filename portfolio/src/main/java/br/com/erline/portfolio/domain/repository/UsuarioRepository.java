package br.com.erline.portfolio.domain.repository;

import br.com.erline.portfolio.domain.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(UUID id);
}
