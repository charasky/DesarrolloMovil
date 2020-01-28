package org.lapoderosa.app.user;

public class Usuario {
    private String nombre;
    private String apellido;
    private String usuario;
    private String asamblea;

    public Usuario(String usuario, String nombre, String apellido, String asamblea) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.asamblea = asamblea;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAsamblea() {
        return asamblea;
    }

    public String getFullName(){
        return this.nombre + " " + this.apellido;
    }
}
