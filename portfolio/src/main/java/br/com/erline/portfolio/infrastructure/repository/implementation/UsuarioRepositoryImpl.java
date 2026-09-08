package br.com.erline.portfolio.infrastructure.repository.implementation;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.repository.UsuarioRepository;
import br.com.erline.portfolio.infrastructure.persistence.mapper.UsuarioMapper;
import br.com.erline.portfolio.infrastructure.persistence.model.UsuarioModel;
import br.com.erline.portfolio.infrastructure.repository.jpa.UsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioModel entity = UsuarioMapper.toEntity(usuario);
        UsuarioModel saved = jpaRepository.save(entity);
        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UsuarioMapper::toDomain);
    }
}
