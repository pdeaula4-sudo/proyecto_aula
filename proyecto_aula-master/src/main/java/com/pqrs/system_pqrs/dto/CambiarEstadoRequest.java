package com.pqrs.system_pqrs.dto;

import com.pqrs.system_pqrs.document.enums.EstadoPeticion;
import jakarta.validation.constraints.NotNull;

public class CambiarEstadoRequest {

    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoPeticion nuevoEstado;

    private String responsableId;
    private String observacion;

    public EstadoPeticion getNuevoEstado() { return nuevoEstado; }
    public void setNuevoEstado(EstadoPeticion nuevoEstado) { this.nuevoEstado = nuevoEstado; }

    public String getResponsableId() { return responsableId; }
    public void setResponsableId(String responsableId) { this.responsableId = responsableId; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
