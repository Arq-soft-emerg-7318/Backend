package com.nexora.nexorabackend.shared.Infrastructure.persistence;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public FileEntity() {}

    public FileEntity(String filename, String storagePath, String contentType, Long size) {
        this.filename = filename;
        this.storagePath = storagePath;
        this.contentType = contentType;
        this.size = size;
    }

    public Integer getId() { return id; }
    public String getFilename() { return filename; }
    public String getStoragePath() { return storagePath; }
    public String getContentType() { return contentType; }
    public Long getSize() { return size; }
    public Instant getCreatedAt() { return createdAt; }
}
