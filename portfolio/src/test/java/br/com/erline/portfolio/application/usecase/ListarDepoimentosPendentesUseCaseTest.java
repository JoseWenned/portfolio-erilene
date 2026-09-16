package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarDepoimentosPendentesUseCaseTest {

    private DepoimentoRepository depoimentoRepository;
    private ListarDepoimentosPendentesUseCase useCase;

    @BeforeEach
    void setUp() {
        depoimentoRepository = mock(DepoimentoRepository.class);
        useCase = new ListarDepoimentosPendentesUseCase(
                depoimentoRepository
        );
    }

    @Test
    void deveListarApenasDepoimentosPendentes() {
        Depoimento depoimento = new Depoimento(
                "João",
                "Excelente profissional!",
                5,
                "/uploads/depoimentos/joao.jpg"
        );

        when(
                depoimentoRepository.findByStatus(
                        StatusDepoimento.PENDENTE
                )
        ).thenReturn(List.of(depoimento));

        List<DepoimentoOutput> resultado = useCase.execute();

        assertEquals(1, resultado.size());

        DepoimentoOutput output = resultado.get(0);

        assertEquals(
                depoimento.getId(),
                output.id()
        );
        assertEquals(
                depoimento.getNome(),
                output.nome()
        );
        assertEquals(
                depoimento.getComentario(),
                output.comentario()
        );
        assertEquals(
                depoimento.getNota(),
                output.nota()
        );
        assertEquals(
                depoimento.getFotoUrl(),
                output.fotoUrl()
        );
        assertEquals(
                StatusDepoimento.PENDENTE,
                output.status()
        );

        verify(depoimentoRepository)
                .findByStatus(StatusDepoimento.PENDENTE);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDepoimentosPendentes() {
        when(
                depoimentoRepository.findByStatus(
                        StatusDepoimento.PENDENTE
                )
        ).thenReturn(List.of());

        List<DepoimentoOutput> resultado = useCase.execute();

        assertEquals(0, resultado.size());

        verify(depoimentoRepository)
                .findByStatus(StatusDepoimento.PENDENTE);
    }
}

