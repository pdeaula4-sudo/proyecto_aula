package com.pqrs.system_pqrs.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Respuesta")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Respuesta")
    private Long idRespuesta;

    @Column(name = "peticionID", nullable = false)
    private Long peticionId;

    @Column(name = "miembroID", nullable = false)
    private Long miembroId;

    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Respuesta() {}

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