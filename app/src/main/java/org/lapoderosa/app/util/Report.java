package org.lapoderosa.app.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatoFecha_DMA = new SimpleDateFormat("dd-MM-yyyy");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatoFecha_AMD = new SimpleDateFormat("yyyy-MM-dd");
    private String idReporte;
    private String pais;
    private String ciudad;
    private String fecha;
    private String v_nombre;
    private String v_apellido;

    public Report(String idReporte, String pais, String ciudad, String fecha, String v_nombre, String v_apellido) {
        this.idReporte = idReporte;
        this.pais = pais;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.v_nombre = v_nombre;
        this.v_apellido = v_apellido;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() { return ciudad; }

    public String getFullName(){ return this.v_nombre + " " + this.v_apellido; }

    public String getFecha() {
        return cambiarFormatoFecha(this.fecha);
    }

    private static String cambiarFormatoFecha(String fecha){
        Date fechaReporte;
        try{
            fechaReporte = formatoFecha_AMD.parse(fecha);
            fecha = formatoFecha_DMA.format(fechaReporte);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha;
    }
}
