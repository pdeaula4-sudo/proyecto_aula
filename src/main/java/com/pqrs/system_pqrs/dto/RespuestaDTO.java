package com.pqrs.system_pqrs.dto;

import java.time.LocalDateTime;

public class RespuestaDTO {
    private Long idRespuesta;
    private Long peticionId;
    private Long miembroId;
    private String texto;
    private LocalDateTime fecha;

    public RespuestaDTO() {}

    public Long getIdRespuesta() { return idRespuesta; }
    public void setIdRespuesta(Long idRespuesta) { this.idRespuesta = idRespuesta; }

    public Long getPeticionId() { return peticionId; }
    public void setPeticionId(Long peticionId) { this.peticionId = peticionId; }

    public Long getMiembroId() { return miembroId; }
    public void setMiembroId(Long miembroId) { this.miembroId = miembroId; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}