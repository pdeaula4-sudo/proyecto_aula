package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.document.embedded.EvidenciaEmbedded;
import com.pqrs.system_pqrs.service.PeticionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${app.upload.dir:./uploads/}")
    private String uploadDir;

    private final PeticionService peticionService;

    public FileUploadController(PeticionService peticionService) {
        this.peticionService = peticionService;
    }

    @PostMapping("/evidencia")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadEvidencia(@RequestParam("file") MultipartFile file,
                                             @RequestParam("peticionId") String peticionId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Archivo vacío"));
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            String url = "/uploads/" + fileName;
            EvidenciaEmbedded evidencia = new EvidenciaEmbedded(
                    file.getOriginalFilename(), url, LocalDateTime.now());

            peticionService.agregarEvidencia(peticionId, evidencia);

            return ResponseEntity.ok(Map.of(
                    "nombreArchivo", file.getOriginalFilename(),
                    "url", url,
                    "peticionId", peticionId));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al guardar el archivo"));
        }
    }
}
