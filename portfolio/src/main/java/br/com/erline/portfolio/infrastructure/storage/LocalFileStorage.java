package br.com.erline.portfolio.infrastructure.storage;

import br.com.erline.portfolio.application.port.FileStorage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class LocalFileStorage implements FileStorage {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Map<String, String> ALLOWED_CONTENT_TYPES = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp"
    );

    private final Path uploadDirectory;

    public LocalFileStorage() {
        this.uploadDirectory = Paths.get(
                "uploads",
                "depoimentos"
        ).toAbsolutePath().normalize();

        createUploadDirectory();
    }

    @Override
    public String store(MultipartFile file) {

        validateFile(file);

        String extension =
                ALLOWED_CONTENT_TYPES.get(file.getContentType());

        String fileName =
                UUID.randomUUID() + extension;

        Path targetFile =
                uploadDirectory.resolve(fileName).normalize();

        if (!targetFile.getParent().equals(uploadDirectory)) {
            throw new IllegalArgumentException(
                    "Caminho do arquivo inválido."
            );
        }

        try (InputStream inputStream = file.getInputStream()) {

            Files.copy(
                    inputStream,
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Não foi possível salvar a imagem.",
                    exception
            );
        }

        return "/uploads/depoimentos/" + fileName;
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "A imagem é obrigatória."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "A imagem deve ter no máximo 5 MB."
            );
        }

        String contentType = file.getContentType();

        if (!ALLOWED_CONTENT_TYPES.containsKey(contentType)) {
            throw new IllegalArgumentException(
                    "Formato de imagem não permitido. "
                            + "Utilize JPG, PNG ou WEBP."
            );
        }

        String originalFilename =
                StringUtils.cleanPath(
                        file.getOriginalFilename() == null
                                ? ""
                                : file.getOriginalFilename()
                );

        if (originalFilename.contains("..")) {
            throw new IllegalArgumentException(
                    "Nome de arquivo inválido."
            );
        }
    }

    private void createUploadDirectory() {

        try {
            Files.createDirectories(uploadDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Não foi possível criar o diretório de uploads.",
                    exception
            );
        }
    }
}