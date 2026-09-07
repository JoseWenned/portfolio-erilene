package br.com.erline.portfolio.domain.exception;

import java.util.UUID;

public class DepoimentoNotFoundException extends RuntimeException {
    public DepoimentoNotFoundException(UUID id) {
        super("Depoimento não encontrado: " + id);
    }
}