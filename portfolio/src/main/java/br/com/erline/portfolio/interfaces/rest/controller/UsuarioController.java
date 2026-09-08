package br.com.erline.portfolio.interfaces.rest.controller;

import br.com.erline.portfolio.application.dto.UsuarioOutput;
import br.com.erline.portfolio.application.usecase.CriarUsuarioUseCase;
import br.com.erline.portfolio.interfaces.rest.dto.request.CriarUsuarioRequest;
import br.com.erline.portfolio.interfaces.rest.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Administração de usuários")
@SecurityRequirement(name = "basicAuth")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar usuário administrador")
    public ResponseEntity<UsuarioResponse> criar(
            @Valid @RequestBody CriarUsuarioRequest request
    ) {
        UsuarioOutput output = criarUsuarioUseCase.execute(request.toInput());
        UsuarioResponse response = UsuarioResponse.from(output);

        return ResponseEntity
                .created(URI.create("/api/usuarios/" + response.id()))
                .body(response);
    }
}