package com.pqrs.system_pqrs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TipoPeticion")
public class TipoPeticion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_TipoPeticion")
    private Long idTipoPeticion;

    @Column(name = "nombre_TipoPeticion", nullable = false)
    private String nombreTipoPeticion;

    @Column(name = "tiempo_peticion")
    private Integer tiempoPeticion;

    public TipoPeticion() {}

    public Long getIdTipoPeticion() { return idTipoPeticion; }
    public void setIdTipoPeticion(Long idTipoPeticion) { this.idTipoPeticion = idTipoPeticion; }

    public String getNombreTipoPeticion() { return nombreTipoPeticion; }
    public void setNombreTipoPeticion(String nombreTipoPeticion) { this.nombreTipoPeticion = nombreTipoPeticion; }

    public Integer getTiempoPeticion() { return tiempoPeticion; }
    public void setTiempoPeticion(Integer tiempoPeticion) { this.tiempoPeticion = tiempoPeticion; }
}