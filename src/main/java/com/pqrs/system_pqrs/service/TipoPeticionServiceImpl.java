package com.pqrs.system_pqrs.service;

import com.pqrs.system_pqrs.entity.TipoPeticion;
import com.pqrs.system_pqrs.repository.TipoPeticionRerpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TipoPeticionServiceImpl implements TipoPeticionService {

    @Autowired
    private TipoPeticionRerpository tipoPeticionRepository;

    @Override
    public List<TipoPeticion> findAll() {
        return tipoPeticionRepository.findAll();
    }

    @Override
    public Optional<TipoPeticion> findById(Long id) {
        return tipoPeticionRepository.findById(id);
    }

    @Override
    public TipoPeticion save(TipoPeticion tipoPeticion) {
        return tipoPeticionRepository.save(tipoPeticion);
    }

    @Override
    public void deleteById(Long id) {
        tipoPeticionRepository.deleteById(id);
    }
}