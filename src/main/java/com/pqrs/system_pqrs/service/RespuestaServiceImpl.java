package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.Respuesta;
import com.pqrs.system_pqrs.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public List<Respuesta> findAll() {
        return respuestaRepository.findAll();
    }

    @Override
    public Optional<Respuesta> findById(Long id) {
        return respuestaRepository.findById(id);
    }

    @Override
    public Respuesta save(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    @Override
    public void deleteById(Long id) {
        respuestaRepository.deleteById(id);
    }

    @Override
    public List<Respuesta> findByPeticionId(Long peticionId) {
        return respuestaRepository.findByPeticionId(peticionId);
    }

    @Override
    public List<Respuesta> findByMiembroId(Long miembroId) {
        return respuestaRepository.findByMiembroId(miembroId);
    }
}