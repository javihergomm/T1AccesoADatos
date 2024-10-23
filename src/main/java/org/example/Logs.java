package org.example;

import java.time.LocalDateTime;

public class Logs {

    private String fecha;
    private String nivel;
    private String mensaje;

    public Logs (String fecha, String nivel, String mensaje) {
        this.fecha = fecha;
        this.nivel = nivel;
        this.mensaje = mensaje;

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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

    @Override
    public String toString() {
        return "Logs{" +
                "fecha=" + fecha +
                ", nivel='" + nivel + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}


