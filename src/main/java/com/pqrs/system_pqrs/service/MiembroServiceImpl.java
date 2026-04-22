package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Miembro;
import com.pqrs.system_pqrs.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MiembroServiceImpl implements MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Override
    public List<Miembro> findAll() {
        return miembroRepository.findAll();
    }

    @Override
    public Optional<Miembro> findById(Long id) {
        return miembroRepository.findById(id);
    }

    @Override
    public Miembro save(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    @Override
    public void deleteById(Long id) {
        miembroRepository.deleteById(id);
    }

    @Override
    public Optional<Miembro> findByCorreo(String correo) {
        return miembroRepository.findByCorreoMiembro(correo);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return miembroRepository.existsByCorreoMiembro(correo);
    }
}