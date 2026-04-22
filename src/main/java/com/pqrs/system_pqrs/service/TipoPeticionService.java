package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.TipoPeticion;
import java.util.List;
import java.util.Optional;

public interface TipoPeticionService {
    List<TipoPeticion> findAll();
    Optional<TipoPeticion> findById(Long id);
    TipoPeticion save(TipoPeticion tipoPeticion);
    void deleteById(Long id);
}