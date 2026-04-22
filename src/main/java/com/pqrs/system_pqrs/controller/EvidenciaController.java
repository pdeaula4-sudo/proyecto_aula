package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.entity.Evidencia;
import com.pqrs.system_pqrs.service.EvidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evidencias")
@CrossOrigin(origins = "http://localhost:4200")
public class EvidenciaController {

    @Autowired
    private EvidenciaService evidenciaService;

    @GetMapping
    public List<Evidencia> getAllEvidencias() {
        return evidenciaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evidencia> getEvidenciaById(@PathVariable Long id) {
        Optional<Evidencia> evidencia = evidenciaService.findById(id);
        return evidencia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Evidencia createEvidencia(@RequestBody Evidencia evidencia) {
        return evidenciaService.save(evidencia);
    }

    @GetMapping("/peticion/{peticionId}")
    public List<Evidencia> getEvidenciasByPeticion(@PathVariable Long peticionId) {
        return evidenciaService.findByPeticionId(peticionId);
    }
}