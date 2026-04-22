package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.dto.FileUploadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @PostMapping("/evidencia")
    public ResponseEntity<FileUploadDTO> uploadEvidencia(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("peticionId") Long peticionId) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.write(filePath, file.getBytes());

            FileUploadDTO response = new FileUploadDTO();
            response.setFileName(file.getOriginalFilename());
            response.setFilePath("/uploads/" + fileName);
            response.setPeticionId(peticionId);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}