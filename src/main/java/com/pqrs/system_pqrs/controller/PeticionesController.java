package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.dto.PeticionesCreateDTO;
import com.pqrs.system_pqrs.entity.Peticiones;
import com.pqrs.system_pqrs.service.PeticionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/peticiones")
@CrossOrigin(origins = "http://localhost:4200")
public class PeticionesController {

    @Autowired
    private PeticionesService peticionesService;

    @GetMapping
    public List<Peticiones> getAllPeticiones() {
        return peticionesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peticiones> getPeticionById(@PathVariable Long id) {
        Optional<Peticiones> peticion = peticionesService.findById(id);
        return peticion.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Peticiones createPeticion(@RequestBody PeticionesCreateDTO peticionDTO,
                                     @RequestHeader("miembroId") Long miembroId) {
        Peticiones peticion = new Peticiones();
        peticion.setDescripcionPeticion(peticionDTO.getDescripcionPeticion());
        peticion.setTipoPeticionId(peticionDTO.getTipoPeticionId());
        peticion.setMiembroId(miembroId);
        peticion.setEstadoId(1L); // Estado por defecto: Pendiente
        return peticionesService.save(peticion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peticiones> updatePeticion(@PathVariable Long id, @RequestBody Peticiones peticionDetails) {
        Optional<Peticiones> peticionOpt = peticionesService.findById(id);
        if (peticionOpt.isPresent()) {
            Peticiones peticion = peticionOpt.get();
            peticion.setDescripcionPeticion(peticionDetails.getDescripcionPeticion());
            peticion.setEstadoId(peticionDetails.getEstadoId());
            peticion.setTipoPeticionId(peticionDetails.getTipoPeticionId());
            peticion.setResponsableId(peticionDetails.getResponsableId());
            return ResponseEntity.ok(peticionesService.save(peticion));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeticion(@PathVariable Long id) {
        if (peticionesService.findById(id).isPresent()) {
            peticionesService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/miembro/{miembroId}")
    public List<Peticiones> getPeticionesByMiembro(@PathVariable Long miembroId) {
        return peticionesService.findByMiembroId(miembroId);
    }

    @GetMapping("/estado/{estadoId}")
    public List<Peticiones> getPeticionesByEstado(@PathVariable Long estadoId) {
        return peticionesService.findByEstadoId(estadoId);
    }
}