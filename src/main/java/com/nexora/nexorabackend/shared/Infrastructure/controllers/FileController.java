package com.nexora.nexorabackend.shared.Infrastructure.controllers;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.nexorabackend.shared.Infrastructure.persistence.FileEntity;
import com.nexora.nexorabackend.shared.Infrastructure.persistence.FileRepository;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id) {
        Optional<FileEntity> opt = fileRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        FileEntity fe = opt.get();
        try {
            Path file = Path.of(fe.getStoragePath());
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists()) return ResponseEntity.notFound().build();

            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            try { mediaType = MediaType.parseMediaType(fe.getContentType()); } catch (Exception ex) {}

        ContentDisposition cd = ContentDisposition.builder("inline")
            .filename(fe.getFilename(), StandardCharsets.UTF_8)
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(cd);

        return ResponseEntity.ok()
            .headers(headers)
            .contentType(mediaType)
            .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
