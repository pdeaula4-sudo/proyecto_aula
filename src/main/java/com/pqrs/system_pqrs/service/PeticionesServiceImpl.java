package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Peticiones;
import com.pqrs.system_pqrs.repository.PeticionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PeticionesServiceImpl implements PeticionesService {

    @Autowired
    private PeticionesRepository peticionesRepository;

    @Override
    public List<Peticiones> findAll() {
        return peticionesRepository.findAll();
    }

    @Override
    public Optional<Peticiones> findById(Long id) {
        return peticionesRepository.findById(id);
    }

    @Override
    public Peticiones save(Peticiones peticiones) {
        return peticionesRepository.save(peticiones);
    }

    @Override
    public void deleteById(Long id) {
        peticionesRepository.deleteById(id);
    }

    @Override
    public List<Peticiones> findByMiembroId(Long miembroId) {
        return peticionesRepository.findByMiembroId(miembroId);
    }

    @Override
    public List<Peticiones> findByEstadoId(Long estadoId) {
        return peticionesRepository.findByEstadoId(estadoId);
    }

    @Override
    public List<Peticiones> findByTipoPeticionId(Long tipoPeticionId) {
        return peticionesRepository.findByTipoPeticionId(tipoPeticionId);
    }
}