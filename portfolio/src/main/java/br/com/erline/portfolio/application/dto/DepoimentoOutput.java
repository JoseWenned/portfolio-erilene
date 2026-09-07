package br.com.erline.portfolio.application.dto;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;

import java.time.LocalDateTime;
import java.util.UUID;

public record DepoimentoOutput(
        UUID id,
        String nome,
        String comentario,
        Integer nota,
        String fotoUrl,
        StatusDepoimento status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DepoimentoOutput from(Depoimento depoimento) {
        return new DepoimentoOutput(
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
}