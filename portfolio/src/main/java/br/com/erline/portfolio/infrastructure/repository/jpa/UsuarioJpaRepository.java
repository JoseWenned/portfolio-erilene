package br.com.erline.portfolio.infrastructure.repository.jpa;

import br.com.erline.portfolio.infrastructure.persistence.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository
		extends JpaRepository<UsuarioModel, UUID> {

	Optional<UsuarioModel> findByEmail(String email);
}
