package com.pqrs.system_pqrs.repository;

import com.pqrs.system_pqrs.entity.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {
    List<Evidencia> findByPeticionId(Long peticionId);
}