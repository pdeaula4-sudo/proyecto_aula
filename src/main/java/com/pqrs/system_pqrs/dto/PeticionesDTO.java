package com.pqrs.system_pqrs.dto;

public class PeticionesDTO {
    private Long idPeticion;
    private Long miembroId;
    private String descripcionPeticion;
    private Long estadoId;
    private Long tipoPeticionId;
    private Long responsableId;

    public PeticionesDTO() {}

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