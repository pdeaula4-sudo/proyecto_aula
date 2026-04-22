package com.pqrs.system_pqrs.repository;

import com.pqrs.system_pqrs.entity.Peticiones;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PeticionesRepository extends JpaRepository<Peticiones, Long> {
    List<Peticiones> findByMiembroId(Long miembroId);
    List<Peticiones> findByEstadoId(Long estadoId);
    List<Peticiones> findByTipoPeticionId(Long tipoPeticionId);
}