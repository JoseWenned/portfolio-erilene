package br.com.erline.portfolio.infrastructure.mapper;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.infrastructure.persistence.mapper.DepoimentoMapper;
import br.com.erline.portfolio.infrastructure.persistence.model.DepoimentoModel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DepoimentoMapperTest {

    @Test
    void deveConverterDomainParaEntity() {

        Depoimento depoimento = new Depoimento(
                "Maria",
                "Excelente profissional!",
                5,
                "https://foto.com/maria.jpg"
        );

        DepoimentoModel entity =
                DepoimentoMapper.toEntity(depoimento);

        assertEquals(depoimento.getId(), entity.getId());
        assertEquals(depoimento.getNome(), entity.getNome());
        assertEquals(depoimento.getComentario(), entity.getComentario());
        assertEquals(depoimento.getNota(), entity.getNota());
        assertEquals(depoimento.getFotoUrl(), entity.getFotoUrl());
        assertEquals(depoimento.getStatus(), entity.getStatus());
        assertEquals(depoimento.getCreatedAt(), entity.getCreatedAt());
        assertEquals(depoimento.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    void deveConverterEntityParaDomain() {

        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        DepoimentoModel entity = new DepoimentoModel(
                id,
                "João",
                "Ótimo atendimento!",
                4,
                null,
                StatusDepoimento.APROVADO,
                createdAt,
                updatedAt
        );

        Depoimento depoimento =
                DepoimentoMapper.toDomain(entity);

        assertEquals(id, depoimento.getId());
        assertEquals("João", depoimento.getNome());
        assertEquals("Ótimo atendimento!", depoimento.getComentario());
        assertEquals(4, depoimento.getNota());
        assertNull(depoimento.getFotoUrl());
        assertEquals(StatusDepoimento.APROVADO, depoimento.getStatus());
        assertEquals(createdAt, depoimento.getCreatedAt());
        assertEquals(updatedAt, depoimento.getUpdatedAt());
    }
}