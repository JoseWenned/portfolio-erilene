package br.com.erline.portfolio.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    String store(MultipartFile file);
}