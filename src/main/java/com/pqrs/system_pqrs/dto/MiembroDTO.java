package com.pqrs.system_pqrs.dto;

public class MiembroDTO {
    private Long codeMiembro;
    private String nombreMiembro;
    private String apellidoMiembro;
    private String telMiembro;
    private String correoMiembro;
    private Long rolIdRol;

    public MiembroDTO() {}

    public Long getCodeMiembro() { return codeMiembro; }
    public void setCodeMiembro(Long codeMiembro) { this.codeMiembro = codeMiembro; }

    public String getNombreMiembro() { return nombreMiembro; }
    public void setNombreMiembro(String nombreMiembro) { this.nombreMiembro = nombreMiembro; }

    public String getApellidoMiembro() { return apellidoMiembro; }
    public void setApellidoMiembro(String apellidoMiembro) { this.apellidoMiembro = apellidoMiembro; }

    public String getTelMiembro() { return telMiembro; }
    public void setTelMiembro(String telMiembro) { this.telMiembro = telMiembro; }

    public String getCorreoMiembro() { return correoMiembro; }
    public void setCorreoMiembro(String correoMiembro) { this.correoMiembro = correoMiembro; }

    public Long getRolIdRol() { return rolIdRol; }
    public void setRolIdRol(Long rolIdRol) { this.rolIdRol = rolIdRol; }
}