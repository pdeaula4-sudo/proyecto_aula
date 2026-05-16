package com.pqrs.system_pqrs.document.embedded;

import java.time.LocalDateTime;

public class EvidenciaEmbedded {
    private String nombreArchivo;
    private String url;
    private LocalDateTime fechaSubida;

    public EvidenciaEmbedded() {}

    public EvidenciaEmbedded(String nombreArchivo, String url, LocalDateTime fechaSubida) {
        this.nombreArchivo = nombreArchivo;
        this.url = url;
        this.fechaSubida = fechaSubida;
    }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
}
