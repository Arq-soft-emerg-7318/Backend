package com.nexora.nexorabackend.shared.Infrastructure.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    /**
     * Store the given multipart file and return the created FileEntity id.
     */
    Integer store(MultipartFile file) throws IOException;
}
