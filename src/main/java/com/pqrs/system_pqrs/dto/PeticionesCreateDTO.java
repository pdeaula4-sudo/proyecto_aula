package com.pqrs.system_pqrs.dto;

public class PeticionesCreateDTO {
    private String descripcionPeticion;
    private Long tipoPeticionId;

    public PeticionesCreateDTO() {}

    public String getDescripcionPeticion() { return descripcionPeticion; }
    public void setDescripcionPeticion(String descripcionPeticion) { this.descripcionPeticion = descripcionPeticion; }

    public Long getTipoPeticionId() { return tipoPeticionId; }
    public void setTipoPeticionId(Long tipoPeticionId) { this.tipoPeticionId = tipoPeticionId; }
}