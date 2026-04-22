package com.pqrs.system_pqrs.repository;

import com.pqrs.system_pqrs.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    Optional<Miembro> findByCorreoMiembro(String correoMiembro);
    Optional<Miembro> findByUsername(String username);
    boolean existsByCorreoMiembro(String correoMiembro);
}