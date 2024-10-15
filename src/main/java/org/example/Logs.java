package org.example;

import java.time.LocalDateTime;

public class Logs {

    private LocalDateTime fecha;
    private String nivel;
    private String mensaje;

    public Logs (LocalDateTime fecha, String nivel, String mensaje) {
        this.fecha = fecha;
        this.nivel = nivel;
        this.mensaje = mensaje;

    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
