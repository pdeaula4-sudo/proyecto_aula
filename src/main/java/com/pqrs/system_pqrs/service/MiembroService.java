package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Miembro;
import java.util.List;
import java.util.Optional;

public interface MiembroService {
    List<Miembro> findAll();
    Optional<Miembro> findById(Long id);
    Miembro save(Miembro miembro);
    void deleteById(Long id);
    Optional<Miembro> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}