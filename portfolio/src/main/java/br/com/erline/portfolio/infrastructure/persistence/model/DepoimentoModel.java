package br.com.erline.portfolio.infrastructure.persistence.model;

import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "depoimentos")
public class DepoimentoModel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private Integer nota;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDepoimento status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected DepoimentoModel() {
    }

    public DepoimentoModel(
            UUID id,
            String nome,
            String comentario,
            Integer nota,
            String fotoUrl,
            StatusDepoimento status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.nome = nome;
        this.comentario = comentario;
        this.nota = nota;
        this.fotoUrl = fotoUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getNota() {
        return nota;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public StatusDepoimento getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}