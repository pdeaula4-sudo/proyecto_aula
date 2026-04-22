package com.pqrs.system_pqrs.controller;

import com.pqrs.system_pqrs.entity.Estado;
import com.pqrs.system_pqrs.entity.Rol;
import com.pqrs.system_pqrs.entity.TipoPeticion;
import com.pqrs.system_pqrs.service.EstadoService;
import com.pqrs.system_pqrs.service.RolService;
import com.pqrs.system_pqrs.service.TipoPeticionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "http://localhost:4200")
public class CatalogosController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private TipoPeticionService tipoPeticionService;

    @GetMapping("/estados")
    public List<Estado> getAllEstados() {
        return estadoService.findAll();
    }

    @GetMapping("/roles")
    public List<Rol> getAllRoles() {
        return rolService.findAll();
    }

    @GetMapping("/tipos-peticion")
    public List<TipoPeticion> getAllTiposPeticion() {
        return tipoPeticionService.findAll();
    }
}