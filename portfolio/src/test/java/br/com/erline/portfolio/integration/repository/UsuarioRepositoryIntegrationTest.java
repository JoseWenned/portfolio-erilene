package br.com.erline.portfolio.integration.repository;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.infrastructure.repository.implementation.UsuarioRepositoryImpl;
import br.com.erline.portfolio.infrastructure.repository.jpa.UsuarioJpaRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@Import(UsuarioRepositoryImpl.class)
class UsuarioRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("portfolio_erline")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add(
                "spring.datasource.driver-class-name",
                postgres::getDriverClassName
        );
    }

    @Autowired
    private UsuarioJpaRepository jpaRepository;

    @Autowired
    private UsuarioRepositoryImpl repository;

    @BeforeEach
    void limparBanco() {
        jpaRepository.deleteAll();
    }

    @Test
    void deveSalvarEBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario(
                "admin@example.com",
                "hash-da-senha",
                Role.ADMIN
        );

        Usuario salvo = repository.save(usuario);

        Optional<Usuario> encontrado = repository.findByEmail(
                "admin@example.com"
        );

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getId()).isEqualTo(salvo.getId());
        assertThat(encontrado.get().getEmail())
                .isEqualTo("admin@example.com");
        assertThat(encontrado.get().getSenhaHash())
                .isEqualTo("hash-da-senha");
        assertThat(encontrado.get().getRole()).isEqualTo(Role.ADMIN);
        assertThat(encontrado.get().getCreatedAt()).isNotNull();
        assertThat(encontrado.get().getUpdatedAt()).isNotNull();
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = repository.save(new Usuario(
                "admin@example.com",
                "hash-da-senha",
                Role.ADMIN
        ));

        Optional<Usuario> encontrado = repository.findById(usuario.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail())
                .isEqualTo("admin@example.com");
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoExistir() {
        Optional<Usuario> resultado = repository.findById(UUID.randomUUID());

        assertThat(resultado).isEmpty();
    }
}
