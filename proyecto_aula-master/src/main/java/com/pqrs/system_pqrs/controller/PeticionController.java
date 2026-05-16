package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.document.HistorialPeticion;
import com.pqrs.system_pqrs.document.Peticion;
import com.pqrs.system_pqrs.document.enums.EstadoPeticion;
import com.pqrs.system_pqrs.dto.AgregarRespuestaRequest;
import com.pqrs.system_pqrs.dto.CambiarEstadoRequest;
import com.pqrs.system_pqrs.dto.PeticionCreateRequest;
import com.pqrs.system_pqrs.exception.ResourceNotFoundException;
import com.pqrs.system_pqrs.repository.MiembroRepository;
import com.pqrs.system_pqrs.service.HistorialPeticionService;
import com.pqrs.system_pqrs.service.PeticionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peticiones")
public class PeticionController {

    private final PeticionService peticionService;
    private final HistorialPeticionService historialService;
    private final MiembroRepository miembroRepository;

    public PeticionController(PeticionService peticionService, HistorialPeticionService historialService,
                               MiembroRepository miembroRepository) {
        this.peticionService = peticionService;
        this.historialService = historialService;
        this.miembroRepository = miembroRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Peticion> getAll() {
        return peticionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peticion> getById(@PathVariable String id) {
        return peticionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mis-peticiones")
    public List<Peticion> getMisPeticiones(Authentication auth) {
        String miembroId = miembroRepository.findByCorreoMiembro(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado"))
                .getId();
        return peticionService.findByMiembroId(miembroId);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Peticion> getByEstado(@PathVariable EstadoPeticion estado) {
        return peticionService.findByEstado(estado);
    }

    @PostMapping
    public ResponseEntity<Peticion> crear(@Valid @RequestBody PeticionCreateRequest req,
                                          Authentication auth) {
        return ResponseEntity.ok(peticionService.crear(req, auth.getName()));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Peticion> cambiarEstado(@PathVariable String id,
                                                   @Valid @RequestBody CambiarEstadoRequest req,
                                                   Authentication auth) {
        return ResponseEntity.ok(peticionService.cambiarEstado(id, req, auth.getName()));
    }

    @PostMapping("/{id}/respuestas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Peticion> agregarRespuesta(@PathVariable String id,
                                                      @Valid @RequestBody AgregarRespuestaRequest req,
                                                      Authentication auth) {
        return ResponseEntity.ok(peticionService.agregarRespuesta(id, req, auth.getName()));
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialPeticion>> getHistorial(@PathVariable String id) {
        if (peticionService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Petición no encontrada: " + id);
        }
        return ResponseEntity.ok(historialService.findByPeticionId(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        peticionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
