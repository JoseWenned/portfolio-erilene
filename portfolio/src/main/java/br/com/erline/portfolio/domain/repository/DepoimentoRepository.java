package br.com.erline.portfolio.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;

public interface DepoimentoRepository {
    Depoimento save(Depoimento depoimento);
    List<Depoimento> findByStatus(StatusDepoimento status);
    Optional<Depoimento> findById(UUID id);
}