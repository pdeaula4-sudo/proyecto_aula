package com.pqrs.system_pqrs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Peticiones")
public class Peticiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Peticion")
    private Long idPeticion;

    @Column(name = "miembroID", nullable = false)
    private Long miembroId;

    @Column(name = "descripcion_peticion", nullable = false)
    private String descripcionPeticion;

    @Column(name = "estadoID", nullable = false)
    private Long estadoId;

    @Column(name = "TipoPeticionID", nullable = false)
    private Long tipoPeticionId;

    @Column(name = "ResponsableID")
    private Long responsableId;

    public Peticiones() {}

    public Long getIdPeticion() { return idPeticion; }
    public void setIdPeticion(Long idPeticion) { this.idPeticion = idPeticion; }

    public Long getMiembroId() { return miembroId; }
    public void setMiembroId(Long miembroId) { this.miembroId = miembroId; }

    public String getDescripcionPeticion() { return descripcionPeticion; }
    public void setDescripcionPeticion(String descripcionPeticion) { this.descripcionPeticion = descripcionPeticion; }

    public Long getEstadoId() { return estadoId; }
    public void setEstadoId(Long estadoId) { this.estadoId = estadoId; }

    public Long getTipoPeticionId() { return tipoPeticionId; }
    public void setTipoPeticionId(Long tipoPeticionId) { this.tipoPeticionId = tipoPeticionId; }

    public Long getResponsableId() { return responsableId; }
    public void setResponsableId(Long responsableId) { this.responsableId = responsableId; }
}