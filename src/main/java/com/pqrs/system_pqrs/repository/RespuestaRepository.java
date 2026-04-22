package com.pqrs.system_pqrs.repository;

import com.pqrs.system_pqrs.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByPeticionId(Long peticionId);
    List<Respuesta> findByMiembroId(Long miembroId);
}