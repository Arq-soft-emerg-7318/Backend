package com.nexora.nexorabackend.shared.Infrastructure.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nexora.nexorabackend.shared.Infrastructure.persistence.FileEntity;
import com.nexora.nexorabackend.shared.Infrastructure.persistence.FileRepository;

import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final Path storageRoot;
    private final FileRepository fileRepository;

    public LocalStorageService(@Value("${app.storage.path:uploads}") String storagePath, FileRepository fileRepository) throws IOException {
        this.storageRoot = Path.of(storagePath).toAbsolutePath();
        this.fileRepository = fileRepository;
        Files.createDirectories(this.storageRoot);
    }

    @Override
    @Transactional
    public Integer store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String uid = UUID.randomUUID().toString();
        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) ext = original.substring(original.lastIndexOf('.'));

        String fileName = uid + ext;
        Path target = storageRoot.resolve(fileName);
        try (var in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        FileEntity entity = new FileEntity(original == null ? fileName : original, target.toString(), file.getContentType(), file.getSize());
        FileEntity saved = fileRepository.save(entity);
        return saved.getId();
    }
}
