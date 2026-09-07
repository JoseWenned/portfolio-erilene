package br.com.erline.portfolio.infrastructure.persistence.mapper;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.infrastructure.persistence.model.DepoimentoModel;

public class DepoimentoMapper {

    private DepoimentoMapper() {
    }

    public static DepoimentoModel toEntity(Depoimento depoimento) {
        return new DepoimentoModel(
                depoimento.getId(),
                depoimento.getNome(),
                depoimento.getComentario(),
                depoimento.getNota(),
                depoimento.getFotoUrl(),
                depoimento.getStatus(),
                depoimento.getCreatedAt(),
                depoimento.getUpdatedAt()
        );
    }

    public static Depoimento toDomain(DepoimentoModel model) {
        return Depoimento.reconstituir(
                model.getId(),
                model.getNome(),
                model.getComentario(),
                model.getNota(),
                model.getFotoUrl(),
                model.getStatus(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}