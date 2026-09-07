package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.CriarDepoimentoInput;
import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;

public class CriarDepoimentoUseCase {

    private final DepoimentoRepository depoimentoRepository;

    public CriarDepoimentoUseCase(DepoimentoRepository depoimentoRepository) {
        this.depoimentoRepository = depoimentoRepository;
    }

    public DepoimentoOutput execute(CriarDepoimentoInput input) {

        Depoimento depoimento = new Depoimento(
                input.nome(),
                input.comentario(),
                input.nota(),
                input.fotoUrl()
        );

        Depoimento salvo = depoimentoRepository.save(depoimento);

        return DepoimentoOutput.from(salvo);
    }
}