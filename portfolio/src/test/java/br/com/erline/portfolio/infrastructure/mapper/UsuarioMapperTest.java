package br.com.erline.portfolio.infrastructure.mapper;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.domain.enums.Role;
import br.com.erline.portfolio.infrastructure.persistence.mapper.UsuarioMapper;
import br.com.erline.portfolio.infrastructure.persistence.model.UsuarioModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioMapperTest {

    @Test
    void deveConverterDomainParaEntity() {
        Usuario usuario = new Usuario(
                "admin@example.com",
                "hash-da-senha",
                Role.ADMIN
        );

        UsuarioModel model = UsuarioMapper.toEntity(usuario);

        assertThat(model.getId()).isEqualTo(usuario.getId());
        assertThat(model.getEmail()).isEqualTo(usuario.getEmail());
        assertThat(model.getSenhaHash()).isEqualTo(usuario.getSenhaHash());
        assertThat(model.getRole()).isEqualTo(usuario.getRole());
        assertThat(model.getCreatedAt()).isEqualTo(usuario.getCreatedAt());
        assertThat(model.getUpdatedAt()).isEqualTo(usuario.getUpdatedAt());
    }

    @Test
    void deveConverterEntityParaDomain() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();
        UsuarioModel model = new UsuarioModel(
                id,
                "admin@example.com",
                "hash-da-senha",
                Role.ADMIN,
                createdAt,
                updatedAt
        );

        Usuario usuario = UsuarioMapper.toDomain(model);

        assertThat(usuario.getId()).isEqualTo(id);
        assertThat(usuario.getEmail()).isEqualTo("admin@example.com");
        assertThat(usuario.getSenhaHash()).isEqualTo("hash-da-senha");
        assertThat(usuario.getRole()).isEqualTo(Role.ADMIN);
        assertThat(usuario.getCreatedAt()).isEqualTo(createdAt);
        assertThat(usuario.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
