package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoService {
    List<Estado> findAll();
    Optional<Estado> findById(Long id);
    Estado save(Estado estado);
    void deleteById(Long id);
}