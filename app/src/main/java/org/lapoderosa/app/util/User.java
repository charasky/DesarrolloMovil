package org.lapoderosa.app.util;

public class User {
    private String nombre;
    private String apellido;
    private String usuario;
    private String asamblea;

    public User(String usuario, String nombre, String apellido, String asamblea) {
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
