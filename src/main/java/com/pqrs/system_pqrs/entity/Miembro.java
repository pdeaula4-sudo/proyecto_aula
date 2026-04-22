package com.pqrs.system_pqrs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Miembro")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_Miembro")
    private Long codeMiembro;

    @Column(name = "nombre_Miembro", nullable = false)
    private String nombreMiembro;

    @Column(name = "apellido_Miembro", nullable = false)
    private String apellidoMiembro;

    @Column(name = "tel_Miembro")
    private String telMiembro;

    @Column(name = "correo_Miembro", nullable = false)
    private String correoMiembro;

    @Column(name = "password_Miembro", nullable = false)
    private String passwordMiembro;

    @Column(name = "Rol_id_Rol", nullable = false)
    private Long rolIdRol;

    public Miembro() {}

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

    public String getPasswordMiembro() { return passwordMiembro; }
    public void setPasswordMiembro(String passwordMiembro) { this.passwordMiembro = passwordMiembro; }

    public Long getRolIdRol() { return rolIdRol; }
    public void setRolIdRol(Long rolIdRol) { this.rolIdRol = rolIdRol; }
}