package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.document.Miembro;
import com.pqrs.system_pqrs.document.Peticion;
import com.pqrs.system_pqrs.document.embedded.EvidenciaEmbedded;
import com.pqrs.system_pqrs.document.embedded.MiembroRef;
import com.pqrs.system_pqrs.document.embedded.RespuestaEmbedded;
import com.pqrs.system_pqrs.document.enums.EstadoPeticion;
import com.pqrs.system_pqrs.dto.AgregarRespuestaRequest;
import com.pqrs.system_pqrs.dto.CambiarEstadoRequest;
import com.pqrs.system_pqrs.dto.PeticionCreateRequest;
import com.pqrs.system_pqrs.exception.ResourceNotFoundException;
import com.pqrs.system_pqrs.repository.MiembroRepository;
import com.pqrs.system_pqrs.repository.PeticionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PeticionService {

    private final PeticionRepository peticionRepository;
    private final MiembroRepository miembroRepository;
    private final HistorialPeticionService historialService;

    public PeticionService(PeticionRepository peticionRepository,
                           MiembroRepository miembroRepository,
                           HistorialPeticionService historialService) {
        this.peticionRepository = peticionRepository;
        this.miembroRepository = miembroRepository;
        this.historialService = historialService;
    }

    public List<Peticion> findAll() {
        return peticionRepository.findAll();
    }

    public Optional<Peticion> findById(String id) {
        return peticionRepository.findById(id);
    }

    public List<Peticion> findByMiembroId(String miembroId) {
        return peticionRepository.findByMiembroId(miembroId);
    }

    public List<Peticion> findByEstado(EstadoPeticion estado) {
        return peticionRepository.findByEstado(estado);
    }

    public Peticion crear(PeticionCreateRequest req, String correoMiembro) {
        Miembro miembro = miembroRepository.findByCorreoMiembro(correoMiembro)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado"));

        Peticion peticion = new Peticion();
        peticion.setDescripcion(req.getDescripcion());
        peticion.setTipoPeticion(req.getTipoPeticion());
        peticion.setEstado(EstadoPeticion.PENDIENTE);
        peticion.setFechaCreacion(LocalDateTime.now());
        peticion.setMiembro(new MiembroRef(miembro.getId(), miembro.getNombreCompleto()));

        Peticion saved = peticionRepository.save(peticion);

        historialService.registrarEvento(
                saved.getId(), EstadoPeticion.PENDIENTE, null,
                "Petición recibida por la JAC del Barrio Policarpa");

        return saved;
    }

    public Peticion cambiarEstado(String id, CambiarEstadoRequest req, String correoAdmin) {
        Peticion peticion = peticionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada: " + id));

        MiembroRef responsableRef = null;

        if (req.getResponsableId() != null) {
            Miembro responsable = miembroRepository.findById(req.getResponsableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsable no encontrado"));
            responsableRef = new MiembroRef(responsable.getId(), responsable.getNombreCompleto());
            peticion.setResponsable(responsableRef);
        }

        peticion.setEstado(req.getNuevoEstado());
        Peticion saved = peticionRepository.save(peticion);

        String observacion = req.getObservacion() != null
                ? req.getObservacion()
                : "Estado actualizado a " + req.getNuevoEstado().name();

        historialService.registrarEvento(saved.getId(), req.getNuevoEstado(), responsableRef, observacion);

        return saved;
    }

    public Peticion agregarRespuesta(String id, AgregarRespuestaRequest req, String correoAdmin) {
        Peticion peticion = peticionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada: " + id));

        Miembro admin = miembroRepository.findByCorreoMiembro(correoAdmin)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado"));

        RespuestaEmbedded respuesta = new RespuestaEmbedded(
                admin.getId(), admin.getNombreCompleto(),
                req.getTexto(), LocalDateTime.now());

        peticion.getRespuestas().add(respuesta);
        return peticionRepository.save(peticion);
    }

    public Peticion agregarEvidencia(String id, EvidenciaEmbedded evidencia) {
        Peticion peticion = peticionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada: " + id));
        peticion.getEvidencias().add(evidencia);
        return peticionRepository.save(peticion);
    }

    public void deleteById(String id) {
        if (!peticionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Petición no encontrada: " + id);
        }
        peticionRepository.deleteById(id);
    }
}
