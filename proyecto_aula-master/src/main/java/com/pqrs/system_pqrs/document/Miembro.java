package com.pqrs.system_pqrs.document;

import com.pqrs.system_pqrs.document.enums.RolNombre;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "miembro")
public class Miembro {

    @Id
    private String id;

    private String nombreMiembro;
    private String apellidoMiembro;
    private String telMiembro;

    @Indexed(unique = true)
    private String correoMiembro;

    private String passwordMiembro;

    private RolNombre rol;

    public Miembro() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public RolNombre getRol() { return rol; }
    public void setRol(RolNombre rol) { this.rol = rol; }

    public String getNombreCompleto() {
        return nombreMiembro + " " + apellidoMiembro;
    }
}
