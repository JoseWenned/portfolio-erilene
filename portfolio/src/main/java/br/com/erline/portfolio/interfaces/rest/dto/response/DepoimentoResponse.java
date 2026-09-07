package br.com.erline.portfolio.interfaces.rest.dto.response;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;

import java.time.LocalDateTime;
import java.util.UUID;

public record DepoimentoResponse(
        UUID id,
        String nome,
        String comentario,
        Integer nota,
        String fotoUrl,
        StatusDepoimento status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DepoimentoResponse from(DepoimentoOutput output) {
        return new DepoimentoResponse(
                output.id(),
                output.nome(),
                output.comentario(),
                output.nota(),
                output.fotoUrl(),
                output.status(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}