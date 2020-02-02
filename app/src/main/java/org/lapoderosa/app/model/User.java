package org.lapoderosa.app.model;

public class User {
    private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String asamblea;
    private String enabled = null;

    public User(String usuario, String nombre, String apellido, String asamblea) {
        //falta agregar al php que devuelva el id :v
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.asamblea = asamblea;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
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
