package br.com.erline.portfolio.infrastructure.repository.jpa;

import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.infrastructure.persistence.model.DepoimentoModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepoimentoJpaRepository extends JpaRepository<DepoimentoModel, UUID> {
    List<DepoimentoModel> findByStatus(StatusDepoimento status);
}