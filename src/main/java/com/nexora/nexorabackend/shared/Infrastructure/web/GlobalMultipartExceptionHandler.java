package com.nexora.nexorabackend.shared.Infrastructure.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalMultipartExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalMultipartExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSize(MaxUploadSizeExceededException exc) {
        LOG.warn("File upload exceeded max size: {}", exc.getMessage());
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File too large. Please upload a smaller file.");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipart(MultipartException exc) {
        LOG.warn("Multipart error: {}", exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid multipart request: " + exc.getMessage());
    }
}
