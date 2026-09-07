package br.com.erline.portfolio.application.usecase;

import br.com.erline.portfolio.application.dto.CriarDepoimentoInput;
import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.repository.DepoimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarDepoimentoUseCaseTest {

    @Mock
    private DepoimentoRepository depoimentoRepository;

    private CriarDepoimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CriarDepoimentoUseCase(depoimentoRepository);
    }

    @Test
    void deveCriarDepoimentoComSucesso() {
        CriarDepoimentoInput input = new CriarDepoimentoInput(
                "Maria",
                "Excelente profissional!",
                5,
                "https://exemplo.com/foto.jpg"
        );

        when(depoimentoRepository.save(any(Depoimento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DepoimentoOutput output = useCase.execute(input);

        assertThat(output).isNotNull();
        assertThat(output.nome()).isEqualTo("Maria");
        assertThat(output.comentario()).isEqualTo("Excelente profissional!");
        assertThat(output.nota()).isEqualTo(5);
        assertThat(output.fotoUrl()).isEqualTo("https://exemplo.com/foto.jpg");
        assertThat(output.status().name()).isEqualTo("PENDENTE");
        assertThat(output.id()).isNotNull();
        assertThat(output.createdAt()).isNotNull();
        assertThat(output.updatedAt()).isNotNull();

        verify(depoimentoRepository).save(any(Depoimento.class));
    }
}