package org.lapoderosa.app.model;

public class Movimiento {
    private String usuario;
    private String queHizo;
    private String fecha;
    private String Hora;

    public Movimiento(String usuario, String queHizo, String fecha, String hora) {
        this.usuario = usuario;
        this.queHizo = queHizo;
        this.fecha = fecha;
        Hora = hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getQueHizo() {
        return queHizo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return Hora;
    }
}
