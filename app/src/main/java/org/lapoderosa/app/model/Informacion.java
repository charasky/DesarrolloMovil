package org.lapoderosa.app.model;

public class Informacion {
    private String dato;
    private String informacion;
    private String dbValue;
    private Boolean isEdtEnabled;

    public Informacion(String dato, String informacion, String dbValue, Boolean isEdtEnabled) {
        this.dato = dato;
        this.informacion = informacion;
        this.dbValue = dbValue;
        this.isEdtEnabled = isEdtEnabled;
    }

    public String getDato() {
        return dato;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getDbValue() {
        return dbValue;
    }

    public Boolean getIsEdtEnabled() {
        return isEdtEnabled;
    }
}
