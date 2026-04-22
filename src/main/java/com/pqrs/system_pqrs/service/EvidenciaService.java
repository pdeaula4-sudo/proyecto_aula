package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Evidencia;
import java.util.List;
import java.util.Optional;

public interface EvidenciaService {
    List<Evidencia> findAll();
    Optional<Evidencia> findById(Long id);
    Evidencia save(Evidencia evidencia);
    void deleteById(Long id);
    List<Evidencia> findByPeticionId(Long peticionId);
}