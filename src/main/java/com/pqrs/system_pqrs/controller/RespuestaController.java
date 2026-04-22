package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.entity.Miembro;
import com.pqrs.system_pqrs.entity.Respuesta;
import com.pqrs.system_pqrs.service.MiembroService;
import com.pqrs.system_pqrs.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/respuestas")
@CrossOrigin(origins = "http://localhost:4200")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public List<Respuesta> getAllRespuestas() {
        return respuestaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getRespuestaById(@PathVariable Long id) {
        Optional<Respuesta> respuesta = respuestaService.findById(id);
        return respuesta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRespuesta(@RequestBody Respuesta respuesta,
                                             @RequestHeader("miembroId") Long miembroId) {

        // Verificar si el miembro es administrador (rol 2)
        Optional<Miembro> miembroOpt = miembroService.findById(miembroId);
        if (miembroOpt.isEmpty() || !miembroOpt.get().getRolIdRol().equals(2L)) {
            return ResponseEntity.status(403).body("Solo administradores pueden responder PQRS");
        }

        respuesta.setMiembroId(miembroId);
        respuesta.setFecha(LocalDateTime.now());
        return ResponseEntity.ok(respuestaService.save(respuesta));
    }

    @GetMapping("/peticion/{peticionId}")
    public List<Respuesta> getRespuestasByPeticion(@PathVariable Long peticionId) {
        return respuestaService.findByPeticionId(peticionId);
    }
}