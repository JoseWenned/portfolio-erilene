package br.com.erline.portfolio.domain.entity;

import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DepoimentoTest {

    @Test
    void deveCriarDepoimentoValido() {
        Depoimento depoimento = new Depoimento(
                "José",
                "Excelente profissional!",
                5,
                null
        );

        assertThat(depoimento.getId()).isNotNull();
        assertThat(depoimento.getNome()).isEqualTo("José");
        assertThat(depoimento.getComentario()).isEqualTo("Excelente profissional!");
        assertThat(depoimento.getNota()).isEqualTo(5);
        assertThat(depoimento.getFotoUrl()).isNull();
        assertThat(depoimento.getCreatedAt()).isNotNull();
        assertThat(depoimento.getUpdatedAt()).isNotNull();
    }

    @Test
    void deveIniciarComStatusPendente() {
        Depoimento depoimento = new Depoimento(
                "José",
                "Excelente profissional!",
                5,
                null
        );

        assertThat(depoimento.getStatus())
                .isEqualTo(StatusDepoimento.PENDENTE);
    }

    @Test
    void naoDevePermitirNomeVazio() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "",
                        "Excelente profissional!",
                        5,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O nome é obrigatório.");
    }

    @Test
    void naoDevePermitirNomeNulo() {
        assertThatThrownBy(() ->
                new Depoimento(
                        null,
                        "Excelente profissional!",
                        5,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O nome é obrigatório.");
    }

    @Test
    void naoDevePermitirComentarioVazio() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "José",
                        "",
                        5,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O comentário é obrigatório.");
    }

    @Test
    void naoDevePermitirComentarioNulo() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "José",
                        null,
                        5,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("O comentário é obrigatório.");
    }

    @Test
    void naoDevePermitirNotaMenorQueUm() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "José",
                        "Excelente profissional!",
                        0,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("A nota deve estar entre 1 e 5.");
    }

    @Test
    void naoDevePermitirNotaMaiorQueCinco() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "José",
                        "Excelente profissional!",
                        6,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("A nota deve estar entre 1 e 5.");
    }

    @Test
    void naoDevePermitirNotaNula() {
        assertThatThrownBy(() ->
                new Depoimento(
                        "José",
                        "Excelente profissional!",
                        null,
                        null
                )
        )
                .isInstanceOf(DomainException.class)
                .hasMessage("A nota deve estar entre 1 e 5.");
    }

    @Test
    void deveAprovarDepoimento() {
        Depoimento depoimento = new Depoimento(
                "José",
                "Excelente profissional!",
                5,
                null
        );

        depoimento.aprovar();

        assertThat(depoimento.getStatus())
                .isEqualTo(StatusDepoimento.APROVADO);
    }

    @Test
    void deveRejeitarDepoimento() {
        Depoimento depoimento = new Depoimento(
                "José",
                "Excelente profissional!",
                5,
                null
        );

        depoimento.rejeitar();

        assertThat(depoimento.getStatus())
                .isEqualTo(StatusDepoimento.REJEITADO);
    }
}