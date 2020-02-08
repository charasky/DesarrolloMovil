package org.lapoderosa.app.normal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.Calendar;
import java.util.Map;

public class ReporteActivity extends MasterClass {
    private static final String TAG = "ReporteActivity";
    private TextView tvDateEntrevista, tvDateHecho, tvHoraHecho;
    private DatePickerDialog.OnDateSetListener m1DateSetListener;
    private DatePickerDialog.OnDateSetListener m2DateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button guardar, cancelar;
    private int mHour, mMinute;
    //ENTREVISTADOR
    private String nombreEntrevistador, apellidoEntrevistador, asamblea, fecha;
    //ENTREVISTADO
    private String parentesco;
    //VICTIMA
    private String nombreVictima, apellidoVictima, generoVictima, edadVictima, nacionalidadVictima, documentoVictima, direccionVictima, barrioVictima, telefonoVictima;
    //HECHO POLICIAL
    private String direccionHecho, barrioHecho, ciudadHecho, provinciaHecho, cuantosAcompañan, cualLugar, diaHecho, horaHecho;
    private String ubicacionHecho;
    //FUERZAS INTERVINIENTES
    private String fuerzasIntervinientes, cantidadAgentes, nombresAgentes, apodos, cantidadVehiculos, numMovil, dominio, conductaAgentes;
    //CARACTERISTICAS DEL PROCEDIMIENTO
    private String motivoProcedimiento;
    //MALTRATOS
    private String maltratos;
    //LESIONES
    private String lesiones;
    //MODALIDAD DE DETENCION
    private String posicionDetenido, cuantoTiempoDetenido, traslado, comisaria, esposado;
    //ALLANAMIENTO
    private String ordenAllanamiento;
    private String agresionAllanamiento;
    private String pertenenciasAllanamiento;
    private String omisionPertenencias;
    private String detenidosAllanamiento;
    private String duracionAllanamiento;
    private String posicionDetenidos;
    private String esposados;
    //OMISION AL ACTUAR
    private String mediosDeAsistencia, aQuienAsistencia, denunciaRechazada, violentado;
    //DENUNCIA
    private String hizoDenuncia, dondeDenuncia, porQueNoDenuncia, denunciaFinal;
    //RESULTADO DE LA INVESTIGACION
    private String resultadoInvestigacion, trabajanLosOficiales;

    //**************//
    //ENTREVISTADOR
    private EditText eNombreEntrevistador, eApellidoEntrevistador, eAsamblea, diaEntrevista;
    //ENTREVISTADO
    private EditText edtParentesco;
    //VICTIMA
    private EditText edtNombreVictima, edtApellidoVictima, edtGeneroVictima, edtEdadVictima, edtNacionalidadVictima,
            edtDocumentoVictima, edtDireccionVictima, edtBarrioVictima, edtTelefonoVictima;
    //HECHO POLICIAL
    private EditText edtDireccionHecho, edtBarrioHecho, edtCiudadHecho, edtProvinciaHecho, edtCuantosAcompañan, edtCualLugar;
    //FUERZAS INTERVINIENTES
    private EditText edtFuerzasIntervinientes, edtCantidadAgentes, edtNombresAgentes, edtApodos, edtCantidadVehiculos, edtNumMovil, edtDominio, edtConductaAgentes;
    //CARACTERISTICAS DEL PROCEDIMIENTO
    private RadioButton rbtDelito, rbtAveriguacion, rbtRequiza, rbtNoMotivos;
    //MALTRATOS
    private RadioButton rbtNoMaltratos, rbtVerbales, rbtFisicos, rbtAmenazas, rbtDañoPertenencias, rbtRequizaHumillante, rbtOtraAgresion;
    private EditText edtOtraAgresion;
    //LESIONES
    private RadioButton rbtNoLesiones;
    private EditText edtFisica, edtPsiquica, edtInsomnioPanico, edtOtrosMiedos;
    //MODALIDAD DE DETENCION
    private EditText edtPosicionDetenido, edtCuantoTiempoDetenido, edtDondeTrasladaron, edtCualComisaria;
    private RadioButton rbtNoTrasladaron, rbtSiTrasladaron, rbtNoComisaria , rbtSiComisaria, rbtNoEsposados, rbtSiEsposados;
    //ALLANAMIENTO
    private EditText edtCualesAgresionesDomicilio, edtCualesPertenencias, edtCualesOmitieron, edtCuantosDetenidos, edtTiempoAllanamiento, edtOtraPosicion;
    private RadioButton rbtNoOrdenAllanamiento, rbtSiOrdenAllanamiento, rbtNoAgresionesDomicilio, rbtSiAgresionesDomicilio,
            rbtNoPertenenciasRobadas, rbtSiPertenenciasRobadas, rbtSiOmitieronPertenencias ,rbtNoOmitieronPertenencias,rbtSiDetenidos, rbtNoDetenidos, rbtParado, rbtArrodillado, rbtAcostado,rbtOtro, rbtNoEsposadosAllanamiento, rbtSiEsposadosAllanamiento;
    //OMISION AL ACTUAR

    private EditText edtMedioAsistencia, edtAQuien, edtMotivosDenunciaRechazada;
    private RadioButton rbtNoDenuncia, rbtSiDenuncia, rbtNoViolentado, rbtSiViolentado;
    //DENUNCIA

    private EditText edtDondeDenuncia, edtPorQueNoDenuncia;
    private RadioButton rbtSiHizoDenuncia, rbtNoHizoDenuncia, rbtNoSabeHacerDenuncia;
    //RESULTADO DE LA INVESTIGACION

    private RadioButton rbtCondena, rbtAbsolucion, rbtCausaEnTramite, rbtArchivo, rbtNoSabeResultado, rbtNoTrabajaMas, rbtSiTrabaja;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);
        progressDialog = new ProgressDialog(this);

        constraintLayout = findViewById(R.id.layoutReporte);
        guardar = findViewById(R.id.btnGuardar);
        cancelar = findViewById(R.id.btnCancelar);

        //ENTREVISTADOR
        eNombreEntrevistador = findViewById(R.id.edtNombreEntrevistador);
        eApellidoEntrevistador = findViewById(R.id.edtApellidoEntrevistador);
        eAsamblea = findViewById(R.id.edtAsamblea);
        tvDateEntrevista = findViewById(R.id.tvDateEntrevista);

        //ENTREVISTADO
        edtParentesco = findViewById(R.id.edtParentesco);

        //VICTIMA
        edtNombreVictima = findViewById(R.id.edtNombreVictima);
        edtApellidoVictima = findViewById(R.id.edtApellidoVictima);
        edtGeneroVictima = findViewById(R.id.edtGenero);
        edtEdadVictima = findViewById(R.id.edtEdad);
        edtNacionalidadVictima = findViewById(R.id.edtNacionalidad);
        edtDocumentoVictima = findViewById(R.id.edtDocumento);
        edtDireccionVictima = findViewById(R.id.edtDireccionVictima);
        edtBarrioVictima = findViewById(R.id.edtBarrioVictim);
        edtTelefonoVictima = findViewById(R.id.edtTelefono);

        //HECHO POLICIAL

        tvDateHecho = findViewById(R.id.tvDateHecho);
        tvHoraHecho = findViewById(R.id.tvHoraHecho);
        edtDireccionHecho = findViewById(R.id.edtDireccionHecho);
        edtBarrioHecho = findViewById(R.id.edtBarrioHecho);
        edtCiudadHecho = findViewById(R.id.edtCiudadHecho);
        edtProvinciaHecho = findViewById(R.id.edtProvinciaHecho);
        edtCuantosAcompañan = findViewById(R.id.edtCuantosAcompañan);
        edtCualLugar = findViewById(R.id.edtCualLugar);

        //FUERZAS INTERVINIENTES

        edtFuerzasIntervinientes = findViewById(R.id.edtFuerzasIntervinientes);
        edtCantidadAgentes = findViewById(R.id.edtCantidadAgentes);
        edtNombresAgentes = findViewById(R.id.edtNombresAgentes);
        edtApodos = findViewById(R.id.edtApodos);
        edtCantidadVehiculos = findViewById(R.id.edtCantidadVehiculos);
        edtNumMovil = findViewById(R.id.edtNumMovil);
        edtDominio = findViewById(R.id.edtDominio);
        edtConductaAgentes = findViewById(R.id.edtConductaAgentes);

        //CARACTERISTICAS DEL PROCEDIMIENTO

        //RBT MOTIVO PROCEDIMIENTO

        rbtDelito = findViewById(R.id.rbtDelito);
        rbtAveriguacion = findViewById(R.id.rbtAveriguacion);
        rbtRequiza = findViewById(R.id.rbtRequiza);
        rbtNoMotivos = findViewById(R.id.rbtNoMotivos);

        //RECIBIO MALTRATOS?

        rbtNoMaltratos = findViewById(R.id.rbtNoMaltratos);
        rbtVerbales = findViewById(R.id.rbtVerbales);
        rbtFisicos = findViewById(R.id.rbtFisicos);
        rbtAmenazas = findViewById(R.id.rbtAmenazas);
        rbtDañoPertenencias = findViewById(R.id.rbtDañoPertenencias);
        rbtRequizaHumillante = findViewById(R.id.rbtRequizaHumillante);
        rbtOtraAgresion = findViewById(R.id.rbtOtraAgresion);
        edtOtraAgresion = findViewById(R.id.edtOtraAgresion);

        //LESIONES?

        rbtNoLesiones = findViewById(R.id.rbtNoLesiones);
        edtFisica = findViewById(R.id.edtFisica);
        edtPsiquica = findViewById(R.id.edtPsiquica);
        edtInsomnioPanico = findViewById(R.id.edtInsomnioPanico);
        edtOtrosMiedos = findViewById(R.id.edtOtrosMiedos);

        //MODALIDAD DE DETENCION

        edtPosicionDetenido = findViewById(R.id.edtPosicionDetenido);
        edtCuantoTiempoDetenido = findViewById(R.id.edtCuantoTiempoDetenido);

        //TRASLADO

        rbtNoTrasladaron = findViewById(R.id.rbtNoTrasladaron);
        rbtSiTrasladaron = findViewById(R.id.rbtSiTrasladaron);
        edtDondeTrasladaron = findViewById(R.id.edtDondeTrasladaron);

        //TRASLADO A GARITA/DEPENDENCIA

        rbtNoComisaria = findViewById(R.id.rbtNoComisaria);
        rbtSiComisaria = findViewById(R.id.rbtSiComisaria);
        edtCualComisaria = findViewById(R.id.edtCualComisaria);

        //ESPOSADOS

        rbtNoEsposados = findViewById(R.id.rbtNoEsposados);
        rbtSiEsposados = findViewById(R.id.rbtSiEsposados);

        //ALLANAMIENTOS

        rbtNoOrdenAllanamiento = findViewById(R.id.rbtNoOrdenAllanamiento);
        rbtSiOrdenAllanamiento = findViewById(R.id.rbtSiOrdenAllanamiento);
        rbtNoAgresionesDomicilio = findViewById(R.id.rbtNoAgresionesDomicilio);
        rbtSiAgresionesDomicilio = findViewById(R.id.rbtSiAgresionesDomicilio);
        edtCualesAgresionesDomicilio = findViewById(R.id.edtAgresionAllanamiento);
        rbtNoPertenenciasRobadas = findViewById(R.id.rbtNoPertenenciasRobadas);
        rbtSiPertenenciasRobadas = findViewById(R.id.rbtSiPertenenciasRobadas);
        edtCualesPertenencias = findViewById(R.id.edtPertenenciasAllanamiento);
        rbtNoOmitieronPertenencias = findViewById(R.id.rbtNoOmitieronPertenencias);
        rbtSiOmitieronPertenencias = findViewById(R.id.rbtSiOmitieronPertenencias);
        edtCualesOmitieron = findViewById(R.id.edtCualesOmitieron);
        rbtNoDetenidos = findViewById(R.id.rbtNoDetenidos);
        rbtSiDetenidos = findViewById(R.id.rbtSiDetenidos);
        edtCuantosDetenidos = findViewById(R.id.edtCuantosDetenidos);
        edtTiempoAllanamiento = findViewById(R.id.edtTiempoAllanamiento);
        //modalidad detencion allanamiento
        rbtParado = findViewById(R.id.rbtParado);
        rbtArrodillado = findViewById(R.id.rbtArrodillado);
        rbtAcostado = findViewById(R.id.rbtAcostado);
        rbtOtro = findViewById(R.id.rbtOtro);
        edtOtraPosicion = findViewById(R.id.edtOtraPosicion);
        //esposados
        rbtNoEsposadosAllanamiento = findViewById(R.id.rbtNoEsposadosAllanamiento);
        rbtSiEsposadosAllanamiento = findViewById(R.id.rbtSiEsposadosAllanamiento);

        //OMISION AL ACTUAR

        edtMedioAsistencia = findViewById(R.id.edtMedioAsistencia);
        edtAQuien = findViewById(R.id.edtAQuien);
        rbtNoDenuncia = findViewById(R.id.rbtNoDenuncia);
        rbtSiDenuncia = findViewById(R.id.rbtSiDenuncia);
        edtMotivosDenunciaRechazada = findViewById(R.id.edtMotivosDenunciaRechazada);
        rbtNoViolentado = findViewById(R.id.rbtNoViolentado);
        rbtSiViolentado = findViewById(R.id.rbtSiViolentado);

        //DENUNCIA

        rbtSiHizoDenuncia = findViewById(R.id.rbtSiHizoDenuncia);
        edtDondeDenuncia = findViewById(R.id.edtDondeDenuncia);
        rbtNoHizoDenuncia = findViewById(R.id.rbtNoHizoDenuncia);
        edtPorQueNoDenuncia = findViewById(R.id.edtPorQueNoDenuncia);
        rbtNoSabeHacerDenuncia = findViewById(R.id.rbtNoSabeHacerDenuncia);

        //RESULTADO DE LA INVERTIGACION

        rbtCondena = findViewById(R.id.rbtCondena);
        rbtAbsolucion = findViewById(R.id.rbtAbsolucion);
        rbtCausaEnTramite = findViewById(R.id.rbtCausaEnTramite);
        rbtArchivo = findViewById(R.id.rbtArchivo);
        rbtNoSabeResultado = findViewById(R.id.rbtNoSabeResultado);
        rbtNoTrabajaMas = findViewById(R.id.rbtNoTrabajaMas);
        rbtSiTrabaja = findViewById(R.id.rbtSiTrabaja);


        //logica caracteristica del procedimiento

        listenerMotivoProcedimiento(rbtDelito, rbtAveriguacion, rbtRequiza, rbtNoMotivos);

        //logica maltratos

        listenerMaltratos(rbtNoMaltratos, rbtVerbales, rbtFisicos, rbtAmenazas, rbtDañoPertenencias, rbtRequizaHumillante, edtOtraAgresion, rbtOtraAgresion);

        //logica lesiones

        listenerLesiones(rbtNoLesiones, edtFisica, edtPsiquica, edtInsomnioPanico, edtOtrosMiedos);

        //logica traslado
        rbtYEdtTraslado(edtDondeTrasladaron, rbtNoTrasladaron, rbtSiTrasladaron);

        //COMISARIA/DEPENDENCIA

        rbtYEdtComisaria(edtCualComisaria, rbtNoComisaria, rbtSiComisaria);

        //ESPOSADOS

        listenerEsposado(rbtNoEsposados, rbtSiEsposados);

        //ALLANAMIENTO

        listenerOrdenAllanamiento(rbtNoOrdenAllanamiento, rbtSiOrdenAllanamiento);

        //AGRESIONES ALLANAMIENTO

        rbtYEdtAgresionAllanamiento(edtCualesAgresionesDomicilio, rbtNoAgresionesDomicilio, rbtSiAgresionesDomicilio);

        //pertenencias Domicilio
        rbtYEdtPertenenciasAllanamiento(edtCualesPertenencias, rbtNoPertenenciasRobadas, rbtSiPertenenciasRobadas);

        //omision de pertenencias

        rbtYEdtOmisionPertenencias(edtCualesOmitieron, rbtNoOmitieronPertenencias, rbtSiOmitieronPertenencias);

        //detenidos allanamiento

        rbtYEdtDetenidosAllanamiento(edtCuantosDetenidos, rbtNoDetenidos, rbtSiDetenidos);

        //posicion detenidos allanamiento

        listenerPosicionAllanamiento(rbtParado, rbtArrodillado, rbtAcostado, edtOtraPosicion, rbtOtro);

        //ESPOSADOS ALLANAMIENTO

        listenerEsposadosAllanamiento(rbtNoEsposadosAllanamiento, rbtSiEsposadosAllanamiento);

        //OMISION AL ACTUAR

        //logica concurrio a denuncia y no la tomaron
        listenerDenunciaRechazada(rbtNoDenuncia, edtMotivosDenunciaRechazada, rbtSiDenuncia);

        listenerViolentado(rbtSiViolentado, rbtNoViolentado);

        //logica denuncia

        listenerDenuncia(rbtSiHizoDenuncia, edtDondeDenuncia, rbtNoHizoDenuncia, edtPorQueNoDenuncia, rbtNoSabeHacerDenuncia);

        //RESULTADO INVESTIGACION

        logicaResultadoInvestigacion(rbtCondena, rbtAbsolucion, rbtCausaEnTramite, rbtArchivo, rbtNoSabeResultado);

        //TRABAJAN LOS OFICIALES

        listenerOficiales(rbtSiTrabaja, rbtNoTrabajaMas);


        tvDateEntrevista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        crearListener(tvDateEntrevista),
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        tvDateHecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        crearListener(tvDateHecho),
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        tvHoraHecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog dialog = new TimePickerDialog(
                        ReporteActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHoraHecho.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
                            }
                        }, mHour, mMinute, true);
                dialog.show();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarReporte();
                //ejecutarServicio(getResources().getString(R.string.URL_REPORTE));
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            //hace que el teclado se oculte cuando presionas en otra parte visual del mismo layout
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //vuelve al activity anterior
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de reporte?")
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReporteActivity.this.finish();
                        return;
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //todo enviar reporte
    private void enviarReporte() {
        inicializarStringVariables();
        if (!validateVictima() && !validateEntEntrevistador()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.URL_REPORTE));
            Toast.makeText(getApplicationContext(), "Reporte Guardado con Exito.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        //Entrevistador
        parametros.put("usu_nombre", nombreEntrevistador);
        parametros.put("usu_apellido", apellidoEntrevistador);
        parametros.put("usu_asamblea", asamblea);
        parametros.put("usu_fecha", fecha);
        //Entrevistado
        parametros.put("usu_parentesco_entrevistado", parentesco);
        //Victima
        parametros.put("usu_nombre_victima", nombreVictima);
        parametros.put("usu_apellido_victima", apellidoVictima);
        parametros.put("usu_genero_victima", generoVictima);
        parametros.put("usu_edad_victima", edadVictima);
        parametros.put("usu_nacionalidad_victima", nacionalidadVictima);
        parametros.put("usu_documento_victima", documentoVictima);
        parametros.put("usu_direccion_victima", direccionVictima);
        parametros.put("usu_barrio_victima", barrioVictima);
        parametros.put("usu_telefono_victima", telefonoVictima);
        //Hecho policial
        parametros.put("usu_dia_hecho", diaHecho);
        parametros.put("usu_hora_hecho", horaHecho);
        parametros.put("usu_ubicacion_hecho", ubicacionHecho);
        parametros.put("usu_cuantos_acompañan", cuantosAcompañan);
        parametros.put("usu_cual_lugar", cualLugar);
        //FUERZAS INTERVINIENTES
        parametros.put("usu_fuerzas_intervinientes", fuerzasIntervinientes);
        //agentes
        parametros.put("usu_cantidad_agentes", cantidadAgentes);
        parametros.put("usu_nombres_agentes", nombresAgentes);
        parametros.put("usu_apodos", apodos);
        //vehiculos
        parametros.put("usu_cantidad_vehiculos", cantidadVehiculos);
        parametros.put("usu_num_movil", numMovil);
        parametros.put("usu_dominio", dominio);
        parametros.put("usu_conducta_agentes", conductaAgentes);
        //CARACTERISTICA DEL PROCEDIMIENTO
        parametros.put("usu_motivo_procedimiento", motivoProcedimiento);
        parametros.put("usu_maltratos", maltratos);
        parametros.put("usu_lesiones", lesiones);
        //MODALIDAD DE DETENCION
        parametros.put("usu_posicion_detenido", posicionDetenido);
        parametros.put("usu_tiempo_detenido", cuantoTiempoDetenido);
        //TRASLADO
        parametros.put("usu_traslado", traslado);
        parametros.put("usu_comisaria", comisaria);
        parametros.put("usu_esposado", esposado);
        //ALLANAMIENTO
        parametros.put("usu_orden_allanamiento", ordenAllanamiento);
        parametros.put("usu_agresion_allanamiento", agresionAllanamiento);
        parametros.put("usu_pertenencias_allanamiento", pertenenciasAllanamiento);
        parametros.put("usu_omision_pertenencias", omisionPertenencias);
        parametros.put("usu_detenidos_allanamiento", detenidosAllanamiento);
        parametros.put("usu_duracion_allanamiento", duracionAllanamiento);
        parametros.put("usu_posicion_allanamiento", posicionDetenidos);
        parametros.put("usu_esposados", esposados);
        //OMISION AL ACTUAR
        parametros.put("usu_medios_de_asistencia", mediosDeAsistencia);
        parametros.put("usu_a_quien_asistencia", aQuienAsistencia);
        parametros.put("usu_denuncia_rechazada", denunciaRechazada);
        parametros.put("usu_violentado", violentado);
        parametros.put("usu_denuncia_final", denunciaFinal);
        parametros.put("usu_resultado_investigacion", resultadoInvestigacion);
        parametros.put("usu_trabajan_los_oficiales", trabajanLosOficiales);
    }

    @Override
    protected void inicializarStringVariables() {
        nombreEntrevistador = eNombreEntrevistador.getText().toString().trim();
        apellidoEntrevistador = eApellidoEntrevistador.getText().toString().trim();
        asamblea = eAsamblea.getText().toString().trim();
        fecha = tvDateEntrevista.getText().toString().trim();

        //ENTREVISTADO

        parentesco = edtParentesco.getText().toString().trim();

        //VICTIMA

        nombreVictima = edtNombreVictima.getText().toString().trim();
        apellidoVictima = edtApellidoVictima.getText().toString().trim();
        generoVictima = edtGeneroVictima.getText().toString().trim();
        edadVictima = edtEdadVictima.getText().toString().trim();
        nacionalidadVictima = edtNacionalidadVictima.getText().toString().trim();
        documentoVictima = edtDocumentoVictima.getText().toString().trim();
        direccionVictima = edtDireccionVictima.getText().toString().trim();
        barrioVictima = edtBarrioVictima.getText().toString().trim();
        telefonoVictima = edtTelefonoVictima.getText().toString().trim();

        //HECHO POLICIAL

        diaHecho = tvDateHecho.getText().toString().trim();
        horaHecho = tvHoraHecho.getText().toString().trim();
        ubicacionHecho = edtDireccionHecho.getText().toString() + " " + edtBarrioHecho.getText().toString() + " " + edtCiudadHecho.getText().toString() + " " + edtProvinciaHecho.getText().toString();
        cuantosAcompañan = edtCuantosAcompañan.getText().toString().trim();
        cualLugar = edtCualLugar.getText().toString().trim();
        //FUERZAS INTERVINIENTES

        //agentes

        fuerzasIntervinientes = edtFuerzasIntervinientes.getText().toString().trim();
        cantidadAgentes = edtCantidadAgentes.getText().toString().trim();
        nombresAgentes = edtNombresAgentes.getText().toString().trim();
        apodos = edtApodos.getText().toString().trim();

        //vehiculos
        cantidadVehiculos = edtCantidadVehiculos.getText().toString().trim();
        numMovil = edtNumMovil.getText().toString().trim();
        dominio = edtDominio.getText().toString().trim();
        conductaAgentes = edtConductaAgentes.getText().toString().trim();


        if (!edtFisica.getText().toString().isEmpty()) {
            lesiones += edtFisica.getText().toString() + " ";
        }
        if (!edtPsiquica.getText().toString().isEmpty()) {
            lesiones += edtPsiquica.getText().toString() + " ";
        }
        if (!edtInsomnioPanico.getText().toString().isEmpty()) {
            lesiones += edtInsomnioPanico.getText().toString() + " ";
        }
        if (!edtOtrosMiedos.getText().toString().isEmpty()) {
            lesiones += edtOtrosMiedos.getText().toString() + " ";
        }

        //MODALIDAD DE DETENCION
        posicionDetenido = edtPosicionDetenido.getText().toString();
        cuantoTiempoDetenido = edtCuantoTiempoDetenido.getText().toString();


        duracionAllanamiento = edtTiempoAllanamiento.getText().toString();

        mediosDeAsistencia = edtMedioAsistencia.getText().toString().trim();
        aQuienAsistencia = edtAQuien.getText().toString().trim();
    }

    private boolean validateEntEntrevistador() {
        boolean valid = true;
        if (nombreEntrevistador.isEmpty()) {
            eNombreEntrevistador.setError("Ingrese nombre");
            valid = false;
        }
        if (apellidoEntrevistador.isEmpty()) {
            eApellidoEntrevistador.setError("Ingrese nombre");
            valid = false;
        }
        if (asamblea.isEmpty()) {
            eAsamblea.setError("Ingrese nombre");
            valid = false;
        }
        if (fecha.isEmpty()) {
            tvDateEntrevista.setError("Ingrese nombre");
            valid = false;
        }
        if (parentesco.isEmpty()) {
            edtParentesco.setError("Ingrese nombre");
            valid = false;
        }

        return valid;
    }

    private boolean validateVictima() {
        boolean valid = true;
        if (nombreVictima.isEmpty()) {
            edtNombreVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (apellidoVictima.isEmpty()) {
            edtApellidoVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (generoVictima.isEmpty()) {
            edtGeneroVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (edadVictima.isEmpty()) {
            edtEdadVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (nacionalidadVictima.isEmpty()) {
            edtNacionalidadVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (documentoVictima.isEmpty()) {
            edtDocumentoVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (direccionVictima.isEmpty()) {
            edtDireccionVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (barrioVictima.isEmpty()) {
            edtBarrioVictima.setError("Ingrese nombre");
            valid = false;
        }
        if (telefonoVictima.isEmpty()) {
            edtTelefonoVictima.setError("Ingrese nombre");
            valid = false;
        }

        return valid;
    }


    @Override
    protected void responseConexion(String response) {
        //TODO verificar si lo hace correctamente el shared

        if (!response.isEmpty()) {
            this.chooseInicio();
            //this.chooseInicio(Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser()), Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser()));
            //this.volverLogin();
        }
        /*
        else {
            Toast.makeText(getApplicationContext(), "Verifica los campos obligatorios", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void chooseInicio() {
        //recoge los datos y le asigna a que activity ir
        Boolean admin = Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser());
        Boolean habilitado =  Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser());
        if (admin && habilitado) {
            this.irInicioAdmin();
        }
        if (!admin && habilitado) {
            this.irInicio();
        }
    }

    public DatePickerDialog.OnDateSetListener crearListener(final TextView tvDate) {
        DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                tvDate.setText(date);
            }
        };
        return DateSetListener;
    }

    public void listenerMotivoProcedimiento(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2, final RadioButton rbt3) {
        rbtTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                motivoProcedimiento = rbtTrue.getText().toString();
            }
        });

        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                motivoProcedimiento = rbt1.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbtTrue.setChecked(false);
                rbt3.setChecked(false);
                motivoProcedimiento = rbt2.getText().toString();
            }
        });

        rbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbtTrue.setChecked(false);
                motivoProcedimiento = rbt3.getText().toString();
            }
        });
    }

    public void listenerMaltratos(final RadioButton rbt0, final RadioButton rbt1, final RadioButton rbt2, final RadioButton rbt3, final RadioButton rbt4, final RadioButton rbt5, final EditText edt, final RadioButton rbt6) {
        rbt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt0.getText().toString();
            }
        });
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt2.getText().toString();
            }
        });

        rbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt3.getText().toString();
            }
        });
        rbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt5.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt4.getText().toString();
            }
        });
        rbt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt6.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt5.getText().toString();
            }
        });
        rbt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                maltratos = edt.getText().toString();
            }
        });
    }

    private void listenerLesiones(final RadioButton rbtTrue, final EditText edt1, final EditText edt2, final EditText edt3, final EditText edt4) {
        rbtTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt1);
                limpiarEditText(edt2);
                limpiarEditText(edt3);
                limpiarEditText(edt4);
                lesiones = rbtTrue.getText().toString();
            }
        });

        edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                limpiarEditText(edt2);
                limpiarEditText(edt3);
                limpiarEditText(edt4);
                lesiones = edt1.getText().toString();
            }
        });
        edt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                limpiarEditText(edt1);
                limpiarEditText(edt3);
                limpiarEditText(edt4);
                lesiones = edt2.getText().toString();
            }
        });
        edt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                limpiarEditText(edt1);
                limpiarEditText(edt2);
                limpiarEditText(edt4);
                lesiones = edt3.getText().toString();
            }
        });
        edt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                limpiarEditText(edt1);
                limpiarEditText(edt2);
                limpiarEditText(edt3);
                lesiones = edt4.getText().toString();
            }
        });
    }


    private void listenerEsposado(final RadioButton rbt1, final RadioButton rbt2) {
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                esposado = rbt1.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                esposado = rbt1.getText().toString();
            }
        });
    }

    private void listenerOrdenAllanamiento(final RadioButton rbt1, final RadioButton rbt2) {
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                ordenAllanamiento = rbt1.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                ordenAllanamiento = rbt2.getText().toString();
            }
        });
    }

    private void rbtYEdtAgresionAllanamiento(final EditText text, final RadioButton rbt, final RadioButton rbt2) {
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(text);
                agresionAllanamiento = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                rbt2.setSelected(true);
                agresionAllanamiento = text.getText().toString();

            }
        });

        if(rbt2.isSelected()){
            agresionAllanamiento = text.getText().toString();
        }


    }

    private void rbtYEdtTraslado(final EditText edt, final RadioButton rbt, final RadioButton rbt2) {
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(edt);
                traslado = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                traslado = edt.getText().toString();
            }
        });
    }

    private void rbtYEdtComisaria(final EditText edt, final RadioButton rbt, final RadioButton rbt2) {
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(edt);
                comisaria = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                comisaria = edt.getText().toString();
            }
        });
    }

    private void rbtYEdtPertenenciasAllanamiento(final EditText txt2, final RadioButton rbt, final RadioButton rbt2) {

        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(txt2);
                pertenenciasAllanamiento = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                pertenenciasAllanamiento = txt2.getText().toString();
            }
        });
    }

    private void rbtYEdtOmisionPertenencias(final EditText edt, final RadioButton rbt, final RadioButton rbt2) {
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(edt);
                omisionPertenencias = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                omisionPertenencias = edt.getText().toString();
            }
        });
    }

    private void rbtYEdtDetenidosAllanamiento(final EditText edt, final RadioButton rbt, final RadioButton rbt2) {
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(edt);
                detenidosAllanamiento = rbt.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                detenidosAllanamiento = edt.getText().toString();
            }
        });
    }

    private void listenerEsposadosAllanamiento(final RadioButton rbt1, final RadioButton rbt2) {
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                esposados = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                esposados = rbt2.getText().toString();
            }
        });
    }

    private void listenerPosicionAllanamiento(final RadioButton rbt0, final RadioButton rbt1, final RadioButton rbt2, final EditText edt1, final RadioButton rbt3) {
        rbt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbt0.getText().toString();
            }
        });
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt3.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbt2.getText().toString();
            }
        });
        rbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt0.setChecked(false);
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                posicionDetenidos = edt1.getText().toString();
            }
        });
    }

    private void listenerDenunciaRechazada(final RadioButton rbt1, final EditText edt, final RadioButton rbt2) {
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                limpiarEditText(edt);
                denunciaRechazada = rbt1.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                denunciaRechazada = edt.getText().toString().trim();
            }
        });
    }

    private void listenerViolentado(final RadioButton rbt1, final RadioButton rbt2) {
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt2.setChecked(false);
                violentado = rbt1.getText().toString();
            }
        });

        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                violentado = rbt2.getText().toString();
            }
        });
    }

    private void listenerDenuncia(final RadioButton rbtSi, final EditText edtDonde, final RadioButton rbtNo, final EditText porQueNo, final RadioButton noSabe) {

        rbtSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtNo.setChecked(false);
                noSabe.setChecked(false);
                limpiarEditText(porQueNo);
                denunciaFinal = rbtSi.getText().toString() + edtDonde.getText().toString();
            }
        });
        rbtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtSi.setChecked(false);
                noSabe.setChecked(false);
                limpiarEditText(edtDonde);
                denunciaFinal = rbtNo.getText().toString() + porQueNo.getText().toString();
            }
        });
        noSabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtNo.setChecked(false);
                rbtSi.setChecked(false);
                limpiarEditText(porQueNo);
                limpiarEditText(edtDonde);
                denunciaFinal = noSabe.getText().toString();
            }
        });
    }

    public void logicaResultadoInvestigacion(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2, final RadioButton rbt3, final RadioButton rbt4) {
        rbtTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                resultadoInvestigacion = rbtTrue.getText().toString();
            }
        });

        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                resultadoInvestigacion = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbtTrue.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                resultadoInvestigacion = rbt2.getText().toString();
            }
        });
        rbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbtTrue.setChecked(false);
                rbt4.setChecked(false);
                resultadoInvestigacion = rbt3.getText().toString();
            }
        });
        rbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbtTrue.setChecked(false);
                resultadoInvestigacion = rbt4.getText().toString();
            }
        });
    }

    public void listenerOficiales(final RadioButton rbtSi, final RadioButton rbtNo) {
        rbtSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtNo.setChecked(false);
                trabajanLosOficiales = rbtSi.getText().toString().trim();
            }
        });
        rbtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtSi.setChecked(false);
                trabajanLosOficiales = rbtNo.getText().toString().trim();
            }
        });
    }

    public void limpiarEditText(EditText edtText) {
        edtText.setText("");
        //TODO que el editext pierda el focus al limpiarse.
        //edtText.setFocus;
    }
}
