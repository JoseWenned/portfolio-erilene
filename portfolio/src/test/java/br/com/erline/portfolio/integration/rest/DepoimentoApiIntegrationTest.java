package br.com.erline.portfolio.integration.rest;

import br.com.erline.portfolio.domain.entity.Depoimento;
import br.com.erline.portfolio.infrastructure.repository.implementation.DepoimentoRepositoryImpl;
import br.com.erline.portfolio.infrastructure.repository.jpa.DepoimentoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class DepoimentoApiIntegrationTest {

    private static final String ADMIN_EMAIL = "admin@portfolio.test";
    private static final String ADMIN_PASSWORD = "admin-password";

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
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("portfolio.security.admin.email", () -> ADMIN_EMAIL);
        registry.add("portfolio.security.admin.password", () -> ADMIN_PASSWORD);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepoimentoJpaRepository jpaRepository;

    @Autowired
    private DepoimentoRepositoryImpl repository;

    @BeforeEach
    void limparBanco() {
        jpaRepository.deleteAll();
    }

    @Test
    void deveCriarDepoimentoSemAutenticacao() throws Exception {
        mockMvc.perform(post("/api/depoimentos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Maria",
                                  "comentario": "Excelente profissional!",
                                  "nota": 5,
                                  "fotoUrl": "https://example.com/maria.jpg"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveListarSomenteDepoimentosAprovados() throws Exception {
        Depoimento pendente = new Depoimento(
                "Maria", "Ainda pendente", 5, null
        );
        Depoimento aprovado = new Depoimento(
                "Joao", "Excelente atendimento", 4, null
        );
        aprovado.aprovar();

        repository.save(pendente);
        repository.save(aprovado);

        mockMvc.perform(get("/api/depoimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Joao"))
                .andExpect(jsonPath("$[0].status").value("APROVADO"));
    }

    @Test
    void deveAprovarDepoimentoComCredenciaisDeAdmin() throws Exception {
        Depoimento depoimento = repository.save(new Depoimento(
                "Maria", "Excelente profissional!", 5, null
        ));

        mockMvc.perform(patch("/api/depoimentos/{id}/aprovar", depoimento.getId())
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APROVADO"));
    }

    @Test
    void deveRejeitarDepoimentoComCredenciaisDeAdmin() throws Exception {
        Depoimento depoimento = repository.save(new Depoimento(
                "Maria", "Excelente profissional!", 5, null
        ));

        mockMvc.perform(patch("/api/depoimentos/{id}/rejeitar", depoimento.getId())
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJEITADO"));
    }

    @Test
    void deveRecusarModeracaoSemAutenticacao() throws Exception {
        Depoimento depoimento = repository.save(new Depoimento(
                "Maria", "Excelente profissional!", 5, null
        ));

        mockMvc.perform(patch("/api/depoimentos/{id}/aprovar", depoimento.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRecusarCriacaoComDadosInvalidos() throws Exception {
        mockMvc.perform(post("/api/depoimentos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "",
                                  "comentario": "",
                                  "nota": 6
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.details", hasSize(3)));
    }

    @Test
    void deveCriarUsuarioComCredenciaisDeAdmin() throws Exception {
        String email = "editor-" + UUID.randomUUID() + "@portfolio.test";

        mockMvc.perform(post("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin())
                        .contentType(APPLICATION_JSON)
                        .content(payloadUsuario(email)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.senha").doesNotExist())
                .andExpect(jsonPath("$.senhaHash").doesNotExist());
    }

    @Test
    void deveRecusarCriacaoDeUsuarioSemAutenticacao() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content(payloadUsuario("sem-auth@portfolio.test")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarConflitoAoCriarUsuarioComEmailExistente() throws Exception {
        String email = "duplicado-" + UUID.randomUUID() + "@portfolio.test";
        String payload = payloadUsuario(email);

        mockMvc.perform(post("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin())
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin())
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflito"));
    }

    @Test
    void deveValidarDadosDoNovoUsuario() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, credenciaisAdmin())
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "email-invalido",
                                  "senha": "123",
                                  "role": null
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validação"));
    }

    private String credenciaisAdmin() {
        String credenciais = ADMIN_EMAIL + ":" + ADMIN_PASSWORD;
        String codificado = Base64.getEncoder()
                .encodeToString(credenciais.getBytes(StandardCharsets.UTF_8));
        return "Basic " + codificado;
    }

        private String payloadUsuario(String email) {
                return """
                {
                    "email": "%s",
                    "senha": "senha-segura",
                    "role": "ADMIN"
                }
                """.formatted(email);
        }
}