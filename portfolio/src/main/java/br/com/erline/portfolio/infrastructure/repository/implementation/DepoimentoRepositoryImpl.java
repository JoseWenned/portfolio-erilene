package br.com.erline.portfolio.infrastructure.repository.implementation;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import br.com.erline.portfolio.infrastructure.persistence.mapper.DepoimentoMapper;
import br.com.erline.portfolio.infrastructure.persistence.model.DepoimentoModel;
import br.com.erline.portfolio.infrastructure.repository.jpa.DepoimentoJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DepoimentoRepositoryImpl implements DepoimentoRepository {

    private final DepoimentoJpaRepository jpaRepository;

    public DepoimentoRepositoryImpl(
        DepoimentoJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Depoimento save(Depoimento depoimento) {
        DepoimentoModel entity =
            DepoimentoMapper.toEntity(depoimento);

        DepoimentoModel saved =
            jpaRepository.save(entity);

        return DepoimentoMapper.toDomain(saved);
    }

    @Override
    public List<Depoimento> findByStatus(
            StatusDepoimento status
    ) {
        return jpaRepository.findByStatus(status)
            .stream()
            .map(DepoimentoMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Depoimento> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(DepoimentoMapper::toDomain);
    }
}