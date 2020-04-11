package org.lapoderosa.app.model;

public class Informacion {
    private String id;
    private String vName;
    private String contenido;
    private String dbName;
    private String dbTable;

    public Informacion(String id, String vName, String contenido, String dbName, String dbTable) {
        this.id = id;
        this.vName = vName;
        this.contenido = contenido;
        this.dbName = dbName;
        this.dbTable = dbTable;
    }

    public String getId() {
        return id;
    }

    public String getvName() {
        return vName;
    }

    public String getContenido() {
        return contenido;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbTable() {
        return dbTable;
    }
}
