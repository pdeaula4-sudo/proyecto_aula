package com.pqrs.system_pqrs.dto;

import com.pqrs.system_pqrs.document.enums.TipoPeticionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PeticionCreateRequest {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El tipo de petición es obligatorio")
    private TipoPeticionEnum tipoPeticion;

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public TipoPeticionEnum getTipoPeticion() { return tipoPeticion; }
    public void setTipoPeticion(TipoPeticionEnum tipoPeticion) { this.tipoPeticion = tipoPeticion; }
}
