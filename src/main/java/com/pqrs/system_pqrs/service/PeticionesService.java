package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Peticiones;
import java.util.List;
import java.util.Optional;

public interface PeticionesService {
    List<Peticiones> findAll();
    Optional<Peticiones> findById(Long id);
    Peticiones save(Peticiones peticiones);
    void deleteById(Long id);
    List<Peticiones> findByMiembroId(Long miembroId);
    List<Peticiones> findByEstadoId(Long estadoId);
    List<Peticiones> findByTipoPeticionId(Long tipoPeticionId);
}