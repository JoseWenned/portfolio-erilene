package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.exception.DomainException;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;

import java.util.UUID;

public class AprovarDepoimentoUseCase {

    private final DepoimentoRepository depoimentoRepository;

    public AprovarDepoimentoUseCase(DepoimentoRepository depoimentoRepository) {
        this.depoimentoRepository = depoimentoRepository;
    }

    public DepoimentoOutput execute(UUID id) {

        Depoimento depoimento = depoimentoRepository.findById(id)
            .orElseThrow(() -> new DomainException("Depoimento não encontrado."));

        depoimento.aprovar();

        Depoimento salvo = depoimentoRepository.save(depoimento);

        return DepoimentoOutput.from(salvo);
    }
}