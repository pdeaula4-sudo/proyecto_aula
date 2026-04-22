package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Respuesta;
import java.util.List;
import java.util.Optional;

public interface RespuestaService {
    List<Respuesta> findAll();
    Optional<Respuesta> findById(Long id);
    Respuesta save(Respuesta respuesta);
    void deleteById(Long id);
    List<Respuesta> findByPeticionId(Long peticionId);
    List<Respuesta> findByMiembroId(Long miembroId);
}