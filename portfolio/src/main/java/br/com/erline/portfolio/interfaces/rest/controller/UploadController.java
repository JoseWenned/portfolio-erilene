package br.com.erline.portfolio.interfaces.rest.controller;

import br.com.erline.portfolio.application.port.FileStorage;
import br.com.erline.portfolio.interfaces.rest.dto.response.UploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
@Tag(
        name = "Uploads",
        description = "Upload de arquivos"
)
public class UploadController {

    private final FileStorage fileStorage;

    public UploadController(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @PostMapping(
            value = "/imagem",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Enviar imagem",
            description = "Envia uma imagem para armazenamento local."
    )
    public ResponseEntity<UploadResponse> uploadImagem(
            @RequestParam("file") MultipartFile file
    ) {

        String url = fileStorage.store(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UploadResponse(url));
    }
}