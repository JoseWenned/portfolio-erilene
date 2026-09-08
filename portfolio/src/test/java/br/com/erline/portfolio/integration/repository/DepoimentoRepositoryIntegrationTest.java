package br.com.erline.portfolio.integration.repository;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.domain.enums.StatusDepoimento;
import br.com.erline.portfolio.infrastructure.repository.implementation.DepoimentoRepositoryImpl;
import br.com.erline.portfolio.infrastructure.repository.jpa.DepoimentoJpaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@Import(DepoimentoRepositoryImpl.class)
class DepoimentoRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("portfolio_erline")
                .withUsername("postgres")
                .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(
            DynamicPropertyRegistry registry
    ) {
        registry.add(
            "spring.datasource.url",
            postgres::getJdbcUrl
        );

        registry.add(
            "spring.datasource.username",
            postgres::getUsername
        );

        registry.add(
            "spring.datasource.password",
            postgres::getPassword
        );

        registry.add(
            "spring.datasource.driver-class-name",
            postgres::getDriverClassName
        );
    }

    @Autowired
    private DepoimentoJpaRepository jpaRepository;

    @Autowired
    private DepoimentoRepositoryImpl repository;

    @BeforeEach
    void limparBanco() {
        jpaRepository.deleteAll();
    }

    @Test
    void deveSalvarEDepoisBuscarPorId() {

        Depoimento depoimento = new Depoimento(
            "Maria",
            "Excelente profissional!",
            5,
            "https://example.com/foto.jpg"
        );

        Depoimento salvo = repository.save(depoimento);

        Optional<Depoimento> encontrado =
            repository.findById(salvo.getId());

        assertThat(encontrado).isPresent();

        Depoimento resultado = encontrado.get();

        assertThat(resultado.getId())
            .isEqualTo(depoimento.getId());

        assertThat(resultado.getNome())
            .isEqualTo("Maria");

        assertThat(resultado.getComentario())
            .isEqualTo("Excelente profissional!");

        assertThat(resultado.getNota())
            .isEqualTo(5);

        assertThat(resultado.getFotoUrl())
            .isEqualTo("https://example.com/foto.jpg");

        assertThat(resultado.getStatus())
            .isEqualTo(StatusDepoimento.PENDENTE);

        assertThat(resultado.getCreatedAt())
            .isNotNull();

        assertThat(resultado.getUpdatedAt())
            .isNotNull();
    }

    @Test
    void deveBuscarDepoimentosPorStatus() {

        Depoimento primeiro = new Depoimento(
            "Maria",
            "Excelente profissional!",
            5,
            null
        );

        Depoimento segundo = new Depoimento(
            "João",
            "Ótimo atendimento.",
            4,
            null
        );

        segundo.aprovar();

        repository.save(primeiro);
        repository.save(segundo);

        List<Depoimento> aprovados =
            repository.findByStatus(StatusDepoimento.APROVADO);

        assertThat(aprovados)
            .hasSize(1);

        assertThat(aprovados.get(0).getNome())
            .isEqualTo("João");

        assertThat(aprovados.get(0).getStatus())
            .isEqualTo(StatusDepoimento.APROVADO);
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExistir() {

        UUID idInexistente = UUID.randomUUID();

        Optional<Depoimento> resultado =
            repository.findById(idInexistente);

        assertThat(resultado)
            .isEmpty();
    }
}