package br.com.erline.portfolio.infrastructure.persistence.mapper;

import br.com.erline.portfolio.domain.entity.Usuario;
import br.com.erline.portfolio.infrastructure.persistence.model.UsuarioModel;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioModel toEntity(Usuario usuario) {
        return new UsuarioModel(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getSenhaHash(),
                usuario.getRole(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }

    public static Usuario toDomain(UsuarioModel model) {
        return Usuario.reconstituir(
                model.getId(),
                model.getEmail(),
                model.getSenhaHash(),
                model.getRole(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}
