package br.com.erline.portfolio.interfaces.rest.controller;

import br.com.erline.portfolio.application.dto.DepoimentoOutput;
import br.com.erline.portfolio.application.usecase.AprovarDepoimentoUseCase;
import br.com.erline.portfolio.application.usecase.CriarDepoimentoUseCase;
import br.com.erline.portfolio.application.usecase.ListarDepoimentosUseCase;
import br.com.erline.portfolio.application.usecase.RejeitarDepoimentoUseCase;
import br.com.erline.portfolio.interfaces.rest.dto.request.CriarDepoimentoRequest;
import br.com.erline.portfolio.interfaces.rest.dto.response.DepoimentoResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/depoimentos")
@Tag(name = "Depoimentos", description = "Recebimento e moderação de depoimentos")
public class DepoimentoController {

    private final CriarDepoimentoUseCase criarDepoimentoUseCase;
    private final ListarDepoimentosUseCase listarDepoimentosUseCase;
    private final AprovarDepoimentoUseCase aprovarDepoimentoUseCase;
    private final RejeitarDepoimentoUseCase rejeitarDepoimentoUseCase;

    public DepoimentoController(
            CriarDepoimentoUseCase criarDepoimentoUseCase,
            ListarDepoimentosUseCase listarDepoimentosUseCase,
            AprovarDepoimentoUseCase aprovarDepoimentoUseCase,
            RejeitarDepoimentoUseCase rejeitarDepoimentoUseCase
    ) {
        this.criarDepoimentoUseCase = criarDepoimentoUseCase;
        this.listarDepoimentosUseCase = listarDepoimentosUseCase;
        this.aprovarDepoimentoUseCase = aprovarDepoimentoUseCase;
        this.rejeitarDepoimentoUseCase = rejeitarDepoimentoUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar depoimento", description = "Recebe um depoimento para análise.")
    public ResponseEntity<DepoimentoResponse> criar(
            @Valid @RequestBody CriarDepoimentoRequest request
    ) {
        DepoimentoOutput output =
            criarDepoimentoUseCase.execute(request.toInput());

        DepoimentoResponse response =
            DepoimentoResponse.from(output);

        URI location = URI.create(
            "/api/depoimentos/" + response.id()
        );

        return ResponseEntity
            .created(location)
            .body(response);
    }

    @GetMapping
    @Operation(summary = "Listar depoimentos aprovados")
    public ResponseEntity<List<DepoimentoResponse>> listar() {
        List<DepoimentoResponse> response =
            listarDepoimentosUseCase.execute()
                .stream()
                .map(DepoimentoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar depoimento")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<DepoimentoResponse> aprovar(
            @PathVariable UUID id
    ) {
        DepoimentoOutput output =
            aprovarDepoimentoUseCase.execute(id);

        return ResponseEntity.ok(
            DepoimentoResponse.from(output)
        );
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar depoimento")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<DepoimentoResponse> rejeitar(
        @PathVariable UUID id
    ) {
        DepoimentoOutput output =
            rejeitarDepoimentoUseCase.execute(id);

        return ResponseEntity.ok(
            DepoimentoResponse.from(output)
        );
    }
}