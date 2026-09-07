package br.com.erline.portfolio.domain.entity;

import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.exception.DomainException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Depoimento {

    private UUID id;
    private String nome;
    private String comentario;
    private Integer nota;
    private String fotoUrl;
    private StatusDepoimento status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Depoimento() {
        // Construtor privado para uso do JPA ou frameworks similares
    }

    public Depoimento(
            String nome,
            String comentario,
            Integer nota,
            String fotoUrl
    ) {
        validarNome(nome);
        validarComentario(comentario);
        validarNota(nota);

        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.comentario = comentario.trim();
        this.nota = nota;
        this.fotoUrl = fotoUrl;
        this.status = StatusDepoimento.PENDENTE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public static Depoimento reconstituir(
        UUID id,
        String nome,
        String comentario,
        Integer nota,
        String fotoUrl,
        StatusDepoimento status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        Depoimento depoimento = new Depoimento();

        depoimento.id = id;
        depoimento.nome = nome;
        depoimento.comentario = comentario;
        depoimento.nota = nota;
        depoimento.fotoUrl = fotoUrl;
        depoimento.status = status;
        depoimento.createdAt = createdAt;
        depoimento.updatedAt = updatedAt;

        return depoimento;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DomainException("O nome é obrigatório.");
        }
    }

    private void validarComentario(String comentario) {
        if (comentario == null || comentario.isBlank()) {
            throw new DomainException("O comentário é obrigatório.");
        }
    }

    private void validarNota(Integer nota) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new DomainException(
                    "A nota deve estar entre 1 e 5."
            );
        }
    }

    public void aprovar() {
        this.status = StatusDepoimento.APROVADO;
        this.updatedAt = LocalDateTime.now();
    }

    public void rejeitar() {
        this.status = StatusDepoimento.REJEITADO;
        this.updatedAt = LocalDateTime.now();
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
