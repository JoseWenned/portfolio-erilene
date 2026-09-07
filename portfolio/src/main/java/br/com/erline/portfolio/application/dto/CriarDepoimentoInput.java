package br.com.erline.portfolio.application.dto;

public record CriarDepoimentoInput(
    String nome,
    String comentario,
    Integer nota,
    String fotoUrl
) {}