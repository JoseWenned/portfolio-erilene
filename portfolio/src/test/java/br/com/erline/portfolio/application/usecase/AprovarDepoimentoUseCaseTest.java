package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.exception.DepoimentoNotFoundException;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AprovarDepoimentoUseCaseTest {

    @Mock
    private DepoimentoRepository depoimentoRepository;

    private AprovarDepoimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AprovarDepoimentoUseCase(depoimentoRepository);
    }

    @Test
    void deveAprovarDepoimentoComSucesso() {

        Depoimento depoimento = new Depoimento(
                "Maria",
                "Excelente profissional!",
                5,
                null
        );

        UUID id = depoimento.getId();

        when(depoimentoRepository.findById(id))
                .thenReturn(Optional.of(depoimento));

        when(depoimentoRepository.save(depoimento))
                .thenReturn(depoimento);

        DepoimentoOutput output = useCase.execute(id);

        assertThat(output.status())
                .isEqualTo(StatusDepoimento.APROVADO);

        verify(depoimentoRepository).findById(id);
        verify(depoimentoRepository).save(depoimento);
    }

    @Test
    void deveLancarExcecaoQuandoDepoimentoNaoExistir() {

        UUID id = UUID.randomUUID();

        when(depoimentoRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(DepoimentoNotFoundException.class)
                .hasMessage("Depoimento não encontrado: " + id);

        verify(depoimentoRepository).findById(id);
        verify(depoimentoRepository, never()).save(any());
    }
}