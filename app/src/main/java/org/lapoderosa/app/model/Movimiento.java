package org.lapoderosa.app.model;

public class Movimiento {
    private String usuario;
    private String queHizo;
    private String fecha;
    private String hora;
    private String usuarioInteraccion;

    public Movimiento(String usuario, String queHizo, String fecha, String hora, String usuarioInteraccion) {
        this.usuario = usuario;
        this.queHizo = queHizo;
        this.fecha = fecha;
        this.hora = hora;
        this.usuarioInteraccion = usuarioInteraccion;
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
        return hora;
    }

    public String getUsuarioInteraccion() {
        return usuarioInteraccion;
    }
}
