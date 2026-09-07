package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarDepoimentosUseCaseTest {

    @Mock
    private DepoimentoRepository depoimentoRepository;

    private ListarDepoimentosUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListarDepoimentosUseCase(depoimentoRepository);
    }

    @Test
    void deveListarApenasDepoimentosAprovados() {

        Depoimento depoimento1 = new Depoimento(
                "Maria",
                "Excelente profissional!",
                5,
                null
        );

        Depoimento depoimento2 = new Depoimento(
                "João",
                "Ótimo atendimento!",
                5,
                null
        );

        depoimento1.aprovar();
        depoimento2.aprovar();

        when(depoimentoRepository.findByStatus(StatusDepoimento.APROVADO))
                .thenReturn(List.of(depoimento1, depoimento2));

        List<DepoimentoOutput> resultado = useCase.execute();

        assertThat(resultado).hasSize(2);

        assertThat(resultado)
                .allMatch(depoimento ->
                        depoimento.status() == StatusDepoimento.APROVADO);

        verify(depoimentoRepository)
                .findByStatus(StatusDepoimento.APROVADO);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremDepoimentosAprovados() {

        when(depoimentoRepository.findByStatus(StatusDepoimento.APROVADO))
                .thenReturn(List.of());

        List<DepoimentoOutput> resultado = useCase.execute();

        assertThat(resultado).isEmpty();

        verify(depoimentoRepository)
                .findByStatus(StatusDepoimento.APROVADO);
    }
}