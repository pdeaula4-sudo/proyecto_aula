package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.document.Miembro;
import com.pqrs.system_pqrs.service.MiembroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
@PreAuthorize("hasRole('ADMIN')")
public class MiembroController {

    private final MiembroService miembroService;

    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping
    public List<Miembro> getAll() {
        return miembroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Miembro> getById(@PathVariable String id) {
        return miembroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Miembro> update(@PathVariable String id, @RequestBody Miembro details) {
        return miembroService.findById(id).map(miembro -> {
            miembro.setNombreMiembro(details.getNombreMiembro());
            miembro.setApellidoMiembro(details.getApellidoMiembro());
            miembro.setTelMiembro(details.getTelMiembro());
            miembro.setRol(details.getRol());
            return ResponseEntity.ok(miembroService.save(miembro));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (miembroService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        miembroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
