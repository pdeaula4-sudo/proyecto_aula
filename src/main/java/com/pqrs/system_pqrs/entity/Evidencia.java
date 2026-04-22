package com.pqrs.system_pqrs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Evidencia")
public class Evidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Evidencia")
    private Long idEvidencia;

    @Column(name = "imagen_Evidencia", length = 255)
    private String imagenEvidencia; // Ahora guarda la RUTA del archivo

    @Column(name = "peticionID")
    private Long peticionId;

    public Evidencia() {}

    public Long getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Long idEvidencia) { this.idEvidencia = idEvidencia; }

    public String getImagenEvidencia() { return imagenEvidencia; }
    public void setImagenEvidencia(String imagenEvidencia) { this.imagenEvidencia = imagenEvidencia; }

    public Long getPeticionId() { return peticionId; }
    public void setPeticionId(Long peticionId) { this.peticionId = peticionId; }
}