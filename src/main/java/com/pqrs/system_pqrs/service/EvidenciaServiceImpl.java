package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Evidencia;
import com.pqrs.system_pqrs.repository.EvidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EvidenciaServiceImpl implements EvidenciaService {

    @Autowired
    private EvidenciaRepository evidenciaRepository;

    @Override
    public List<Evidencia> findAll() {
        return evidenciaRepository.findAll();
    }

    @Override
    public Optional<Evidencia> findById(Long id) {
        return evidenciaRepository.findById(id);
    }

    @Override
    public Evidencia save(Evidencia evidencia) {
        return evidenciaRepository.save(evidencia);
    }

    @Override
    public void deleteById(Long id) {
        evidenciaRepository.deleteById(id);
    }

    @Override
    public List<Evidencia> findByPeticionId(Long peticionId) {
        return evidenciaRepository.findByPeticionId(peticionId);
    }
}