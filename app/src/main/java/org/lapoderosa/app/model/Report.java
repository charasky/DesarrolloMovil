package org.lapoderosa.app.model;

public class Report {
    private String idReporte;
    private String pais;
    private String provincia;
    private String fecha;
    private String v_nombre;
    private String v_apellido;
    private String hora;

    public Report(String idReporte, String pais, String provincia, String fecha, String v_nombre, String v_apellido, String hora) {
        this.idReporte = idReporte;
        this.pais = pais;
        this.provincia = provincia;
        this.fecha = fecha;
        this.v_nombre = v_nombre;
        this.v_apellido = v_apellido;
        this.hora = hora;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public String getPais() {
        return pais;
    }

    public String getProvincia() { return provincia; }

    public String getFullName(){ return this.v_nombre + " " + this.v_apellido; }

    public String getFecha() {
        return this.fecha;
    }

    public String getHora() {
        return this.hora;
    }
}
