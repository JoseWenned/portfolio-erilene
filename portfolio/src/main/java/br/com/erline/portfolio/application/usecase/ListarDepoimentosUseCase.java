package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;

import java.util.List;

public class ListarDepoimentosUseCase {

    private final DepoimentoRepository depoimentoRepository;

    public ListarDepoimentosUseCase(DepoimentoRepository depoimentoRepository) {
        this.depoimentoRepository = depoimentoRepository;
    }

    public List<DepoimentoOutput> execute() {

        List<Depoimento> depoimentos =
            depoimentoRepository.findByStatus(StatusDepoimento.APROVADO);

        return depoimentos.stream()
            .map(DepoimentoOutput::from)
            .toList();
    }
}