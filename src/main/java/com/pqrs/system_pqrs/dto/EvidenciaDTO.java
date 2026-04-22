package com.pqrs.system_pqrs.dto;

public class EvidenciaDTO {
    private Long idEvidencia;
    private String imagenEvidencia;
    private Long peticionId;

    public EvidenciaDTO() {}

    public Long getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Long idEvidencia) { this.idEvidencia = idEvidencia; }

    public String getImagenEvidencia() { return imagenEvidencia; }
    public void setImagenEvidencia(String imagenEvidencia) { this.imagenEvidencia = imagenEvidencia; }

    public Long getPeticionId() { return peticionId; }
    public void setPeticionId(Long peticionId) { this.peticionId = peticionId; }
}