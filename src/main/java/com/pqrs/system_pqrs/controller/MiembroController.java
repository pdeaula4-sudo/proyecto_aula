package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.entity.Miembro;
import com.pqrs.system_pqrs.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/miembros")
@CrossOrigin(origins = "http://localhost:4200")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public List<Miembro> getAllMiembros() {
        return miembroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Miembro> getMiembroById(@PathVariable Long id) {
        Optional<Miembro> miembro = miembroService.findById(id);
        return miembro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Miembro createMiembro(@RequestBody Miembro miembro) {
        return miembroService.save(miembro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Miembro> updateMiembro(@PathVariable Long id, @RequestBody Miembro miembroDetails) {
        Optional<Miembro> miembroOpt = miembroService.findById(id);
        if (miembroOpt.isPresent()) {
            Miembro miembro = miembroOpt.get();
            miembro.setNombreMiembro(miembroDetails.getNombreMiembro());
            miembro.setApellidoMiembro(miembroDetails.getApellidoMiembro());
            miembro.setTelMiembro(miembroDetails.getTelMiembro());
            miembro.setCorreoMiembro(miembroDetails.getCorreoMiembro());
            miembro.setPasswordMiembro(miembroDetails.getPasswordMiembro());
            miembro.setRolIdRol(miembroDetails.getRolIdRol());
            return ResponseEntity.ok(miembroService.save(miembro));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Long id) {
        if (miembroService.findById(id).isPresent()) {
            miembroService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}