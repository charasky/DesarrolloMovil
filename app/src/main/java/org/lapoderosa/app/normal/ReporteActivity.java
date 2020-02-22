package org.lapoderosa.app.normal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.SharedPrefManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

//todo verificar que los campos de strings no esten vacios
public class ReporteActivity extends MasterClass {
    private Date date = new Date();
    private String fechaCreacionReporte, horaCreacionReporte;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateHourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
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
    private String direccionHecho, barrioHecho, ciudadHecho, cuantosAcompañan, cualLugar, diaHecho, horaHecho;
    private String ubicacionHecho, provinciaHecho, paisHecho;
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
    private String ordenAllanamiento, agresionAllanamiento, pertenenciasAllanamiento,
            omisionPertenencias, detenidosAllanamiento, duracionAllanamiento, posicionDetenidos, esposados;
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
    private EditText edtDireccionHecho, edtBarrioHecho, edtCiudadHecho, edtProvinciaHecho, edtPaisHecho, edtCuantosAcompañan, edtCualLugar;
    //FUERZAS INTERVINIENTES
    private EditText edtFuerzasIntervinientes, edtCantidadAgentes, edtNombresAgentes, edtApodos, edtCantidadVehiculos, edtNumMovil, edtDominio, edtConductaAgentes;

    //MALTRATOS
    private EditText edtOtraAgresion;
    //LESIONES
    private RadioButton rbtNoLesiones;
    private EditText edtFisica, edtPsiquica, edtInsomnioPanico, edtOtrosMiedos;
    //MODALIDAD DE DETENCION
    private EditText edtPosicionDetenido, edtCuantoTiempoDetenido, edtDondeTrasladaron, edtCualComisaria;
    //ALLANAMIENTO
    private EditText edtCualesAgresionesDomicilio, edtCualesPertenencias, edtCualesOmitieron, edtCuantosDetenidos, edtTiempoAllanamiento, edtOtraPosicion;
    //OMISION AL ACTUAR

    private EditText edtMedioAsistencia, edtAQuien, edtMotivosDenunciaRechazada;
    //DENUNCIA

    private EditText edtDondeDenuncia, edtPorQueNoDenuncia;
    private RadioButton rbtSiHizoDenuncia, rbtNoHizoDenuncia, rbtNoSabeHacerDenuncia;
    //RESULTADO DE LA INVESTIGACION

    private ConstraintLayout constraintLayout;

    private RadioGroup rgProcesamiento, rgMalosTratosAgresiones, rgTrasladoSiNo, rgComisariaSiNo, rgEsposadosSiNo, rgOrdenAllanamientoSiNo,
            rgAgresionDomicilioSiNo, rgPertenenciasRobadasSiNo, rgOmitieronPertenenciasSiNo, rgPersonasDetenidasSiNo, rgPosicionFisicaEleccion, rgEsposadosAllanamientoSiNo,
            rgDenunciaTomadaSiNo, rgViolentadoSiNo, rgEtapaDeInvestigacion, rgOficialesTrabajanSiNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);
        progressDialog = new ProgressDialog(this);

        constraintLayout = findViewById(R.id.layoutReporte);
        guardar = findViewById(R.id.btnGuardar);
        cancelar = findViewById(R.id.btnCancelar);

        //ALLANAMIENTO
        rgOrdenAllanamientoSiNo = findViewById(R.id.rgOrdenAllanamientoSiNo);

        rgAgresionDomicilioSiNo = findViewById(R.id.rgAgresionDomicilioSiNo);
        edtCualesAgresionesDomicilio = findViewById(R.id.edtAgresionAllanamiento);

        rgPertenenciasRobadasSiNo = findViewById(R.id.rgPertenenciasRobadasSiNo);
        edtCualesPertenencias = findViewById(R.id.edtPertenenciasAllanamiento);

        rgOmitieronPertenenciasSiNo = findViewById(R.id.rgOmitieronPertenenciasSiNo);
        edtCualesOmitieron = findViewById(R.id.edtCualesOmitieron);

        rgPersonasDetenidasSiNo = findViewById(R.id.rgPersonasDetenidasSiNo);
        edtCuantosDetenidos = findViewById(R.id.edtCuantosDetenidos);

        edtTiempoAllanamiento = findViewById(R.id.edtTiempoAllanamiento);

        rgPosicionFisicaEleccion = findViewById(R.id.rgPosicionFisicaEleccion);
        edtOtraPosicion = findViewById(R.id.edtOtraPosicion);

        rgEsposadosAllanamientoSiNo = findViewById(R.id.rgEsposadosAllanamientoSiNo);

        //CARACTERISTICAS PROCEDIMIENTO
        rgProcesamiento = findViewById(R.id.rgProcesamiento);

        rgMalosTratosAgresiones = findViewById(R.id.rgMalosTratosAgresiones);
        edtOtraAgresion = findViewById(R.id.edtOtraAgresion);

        rbtNoLesiones = findViewById(R.id.rbtNoLesiones);
        edtFisica = findViewById(R.id.edtFisica);
        edtPsiquica = findViewById(R.id.edtPsiquica);
        edtInsomnioPanico = findViewById(R.id.edtInsomnioPanico);
        edtOtrosMiedos = findViewById(R.id.edtOtrosMiedos);

        //ENTREVISTADO
        edtParentesco = findViewById(R.id.edtParentesco);

        //ENTREVISTADOR
        eNombreEntrevistador = findViewById(R.id.edtNombreEntrevistador);
        eApellidoEntrevistador = findViewById(R.id.edtApellidoEntrevistador);
        eAsamblea = findViewById(R.id.edtAsamblea);
        tvDateEntrevista = findViewById(R.id.tvDateEntrevista);

        //FUERZAS INTERVINIENTES
        edtFuerzasIntervinientes = findViewById(R.id.edtFuerzasIntervinientes);
        edtCantidadAgentes = findViewById(R.id.edtCantidadAgentes);
        edtNombresAgentes = findViewById(R.id.edtNombresAgentes);
        edtApodos = findViewById(R.id.edtApodos);
        edtCantidadVehiculos = findViewById(R.id.edtCantidadVehiculos);
        edtNumMovil = findViewById(R.id.edtNumMovil);
        edtDominio = findViewById(R.id.edtDominio);
        edtConductaAgentes = findViewById(R.id.edtConductaAgentes);

        //HECHO POLICIAL
        tvDateHecho = findViewById(R.id.tvDateHecho);
        tvHoraHecho = findViewById(R.id.tvHoraHecho);
        edtDireccionHecho = findViewById(R.id.edtDireccionHecho);
        edtBarrioHecho = findViewById(R.id.edtBarrioHecho);
        edtCiudadHecho = findViewById(R.id.edtCiudadHecho);
        edtProvinciaHecho = findViewById(R.id.edtProvinciaHecho);
        edtCuantosAcompañan = findViewById(R.id.edtCuantosAcompañan);
        edtCualLugar = findViewById(R.id.edtCualLugar);
        edtPaisHecho = findViewById(R.id.edtPaisHecho);

        //MODALIDAD DE DETENCION
        edtPosicionDetenido = findViewById(R.id.edtPosicionDetenido);
        edtCuantoTiempoDetenido = findViewById(R.id.edtCuantoTiempoDetenido);

        //OMISION AL ACTUAR

        edtMedioAsistencia = findViewById(R.id.edtMedioAsistencia);
        edtAQuien = findViewById(R.id.edtAQuien);

        rgDenunciaTomadaSiNo = findViewById(R.id.rgDenunciaTomadaSiNo);
        edtMotivosDenunciaRechazada = findViewById(R.id.edtMotivosDenunciaRechazada);
        rgViolentadoSiNo = findViewById(R.id.rgViolentadoSiNo);
        //denuncia
        rbtSiHizoDenuncia = findViewById(R.id.rbtSiHizoDenuncia);
        edtDondeDenuncia = findViewById(R.id.edtDondeDenuncia);
        rbtNoHizoDenuncia = findViewById(R.id.rbtNoHizoDenuncia);
        edtPorQueNoDenuncia = findViewById(R.id.edtPorQueNoDenuncia);
        rbtNoSabeHacerDenuncia = findViewById(R.id.rbtNoSabeHacerDenuncia);

        //RESULTADO DE LA INVERTIGACION
        rgEtapaDeInvestigacion = findViewById(R.id.rgEtapaDeInvestigacion);
        rgOficialesTrabajanSiNo = findViewById(R.id.rgOficialesTrabajanSiNo);

        //TRASLADO
        rgTrasladoSiNo = findViewById(R.id.rgTrasladoSiNo);
        edtDondeTrasladaron = findViewById(R.id.edtDondeTrasladaron);
        //esposado
        rgComisariaSiNo = findViewById(R.id.rgComisariaSiNo);
        edtCualComisaria = findViewById(R.id.edtCualComisaria);
        rgEsposadosSiNo = findViewById(R.id.rgEsposadosSiNo);

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
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
        //!validateVictima() && !validateEntrevistador() && validacionRadioButtons()
        if (!validateEntrevistador()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_REPORTE));
            //chooseInicio();
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("fechaReporte", fechaCreacionReporte);
        parametros.put("horaReporte", horaCreacionReporte);
        parametros.put("reporte", this.jsonObject().toString());
    }

    private JSONObject jsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            //ALLANAMIENTO
            jsonObject.put("usu_orden_allanamiento", ordenAllanamiento);
            jsonObject.put("usu_agresion_allanamiento", agresionAllanamiento);
            jsonObject.put("usu_pertenencias_allanamiento", pertenenciasAllanamiento);
            jsonObject.put("usu_omision_pertenencias", omisionPertenencias);
            jsonObject.put("usu_detenidos_allanamiento", detenidosAllanamiento);
            jsonObject.put("usu_duracion_allanamiento", duracionAllanamiento);
            jsonObject.put("usu_esposados", esposados);
            jsonObject.put("usu_posicion_allanamiento", posicionDetenidos);
            //CARACTERISTICA DEL PROCEDIMIENTO
            jsonObject.put("usu_motivo_procedimiento", motivoProcedimiento);
            jsonObject.put("usu_maltratos", maltratos);
            jsonObject.put("usu_lesiones", lesiones);
            //Entrevistado
            jsonObject.put("usu_parentesco_entrevistado", parentesco);
            //Entrevistador
            jsonObject.put("usu_nombre", nombreEntrevistador);
            jsonObject.put("usu_apellido", apellidoEntrevistador);
            jsonObject.put("usu_asamblea", asamblea);
            jsonObject.put("usu_fecha", fecha);
            //FUERZAS INTERVINIENTES
            jsonObject.put("usu_fuerzas_intervinientes", fuerzasIntervinientes);
            //agentes
            jsonObject.put("usu_cantidad_agentes", cantidadAgentes);
            jsonObject.put("usu_nombres_agentes", nombresAgentes);
            jsonObject.put("usu_apodos", apodos);
            //vehiculos
            jsonObject.put("usu_cantidad_vehiculos", cantidadVehiculos);
            jsonObject.put("usu_num_movil", numMovil);
            jsonObject.put("usu_dominio", dominio);
            jsonObject.put("usu_conducta_agentes", conductaAgentes);
            //Hecho policial
            jsonObject.put("usu_dia_hecho", diaHecho);
            jsonObject.put("usu_hora_hecho", horaHecho);
            jsonObject.put("usu_ubicacion_hecho", ubicacionHecho);
            jsonObject.put("usu_cuantos_acompañan", cuantosAcompañan);
            jsonObject.put("usu_cual_lugar", cualLugar);
            jsonObject.put("usu_provincia_hecho", provinciaHecho);
            jsonObject.put("usu_pais_hecho", paisHecho);
            //MODALIDAD DE DETENCION
            jsonObject.put("usu_posicion_detenido", posicionDetenido);
            jsonObject.put("usu_tiempo_detenido", cuantoTiempoDetenido);
            //OMISION AL ACTUAR
            jsonObject.put("usu_medios_de_asistencia", mediosDeAsistencia);
            jsonObject.put("usu_a_quien_asistencia", aQuienAsistencia);
            jsonObject.put("usu_denuncia_rechazada", denunciaRechazada);
            jsonObject.put("usu_violentado", violentado);
            jsonObject.put("usu_denuncia_final", denunciaFinal);
            //RESULTADO INVESTIGACION
            jsonObject.put("usu_resultado_investigacion", resultadoInvestigacion);
            jsonObject.put("usu_trabajan_los_oficiales", trabajanLosOficiales);
            //TRASLADO
            jsonObject.put("usu_traslado", traslado);
            jsonObject.put("usu_comisaria", comisaria);
            jsonObject.put("usu_esposado", esposado);
            //Victima
            jsonObject.put("usu_nombre_victima", nombreVictima);
            jsonObject.put("usu_apellido_victima", apellidoVictima);
            jsonObject.put("usu_genero_victima", generoVictima);
            jsonObject.put("usu_edad_victima", edadVictima);
            jsonObject.put("usu_nacionalidad_victima", nacionalidadVictima);
            jsonObject.put("usu_documento_victima", documentoVictima);
            jsonObject.put("usu_direccion_victima", direccionVictima);
            jsonObject.put("usu_barrio_victima", barrioVictima);
            jsonObject.put("usu_telefono_victima", telefonoVictima);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void inicializarStringVariables() {
        fechaCreacionReporte = dateFormat.format(date);
        horaCreacionReporte = dateHourFormat.format(date);

        //ALLANAMIENTO
        ordenAllanamiento = checkeoRadioGroupSinEditText(rgOrdenAllanamientoSiNo, "Seleccione opcion en Orden de Allanamiento");
        agresionAllanamiento = checkeoRadioGroupConEditText(rgAgresionDomicilioSiNo, edtCualesAgresionesDomicilio, "Seleccione agresion en Allanamiento a Domicilio");
        pertenenciasAllanamiento = checkeoRadioGroupConEditText(rgPertenenciasRobadasSiNo, edtCualesPertenencias, "Seleccione opcion en pertenencias Allanamiento");
        omisionPertenencias = checkeoRadioGroupConEditText(rgOmitieronPertenenciasSiNo, edtCualesOmitieron, "Seleccione opcion en omision de pertenencias");
        detenidosAllanamiento = checkeoRadioGroupConEditText(rgPersonasDetenidasSiNo, edtCuantosDetenidos, "Seleccione opcion en personas detenidas");
        duracionAllanamiento = edtTiempoAllanamiento.getText().toString();
        posicionDetenidos = checkeoRadioGroupConEditText(rgPosicionFisicaEleccion, edtOtraPosicion, "Seleccione opdion en posicion fisica");
        esposados = checkeoRadioGroupSinEditText(rgEsposadosAllanamientoSiNo, "Selecione opcion en esposados");

        //CARACTERISTICAS_PROCESAMIENTO
        motivoProcedimiento = checkeoRadioGroupSinEditText(rgProcesamiento, "Seleccione procedimiento");
        maltratos = checkeoRadioGroupConEditText(rgMalosTratosAgresiones, edtOtraAgresion, "Seleccione opcion en maltratos");
        if (!edtFisica.getText().toString().isEmpty()) {
            lesiones += edtFisica.getText().toString().trim();
        }
        if (!edtPsiquica.getText().toString().isEmpty()) {
            lesiones += edtPsiquica.getText().toString().trim();
        }
        if (!edtInsomnioPanico.getText().toString().isEmpty()) {
            lesiones += edtInsomnioPanico.getText().toString().trim();
        }
        if (!edtOtrosMiedos.getText().toString().isEmpty()) {
            lesiones += edtOtrosMiedos.getText().toString().trim();
        }
        listenerLesiones(rbtNoLesiones, edtFisica, edtPsiquica, edtInsomnioPanico, edtOtrosMiedos);


        //ENTREVISTADO
        parentesco = edtParentesco.getText().toString().trim();

        //ENTREVISTADOR
        nombreEntrevistador = eNombreEntrevistador.getText().toString().trim();
        apellidoEntrevistador = eApellidoEntrevistador.getText().toString().trim();
        asamblea = eAsamblea.getText().toString().trim();
        fecha = tvDateEntrevista.getText().toString().trim();

        //FUERZAS INTERVINIENTES
        //---agentes
        fuerzasIntervinientes = edtFuerzasIntervinientes.getText().toString().trim();
        cantidadAgentes = edtCantidadAgentes.getText().toString().trim();
        nombresAgentes = edtNombresAgentes.getText().toString().trim();
        apodos = edtApodos.getText().toString().trim();
        //---vehiculos
        cantidadVehiculos = edtCantidadVehiculos.getText().toString().trim();
        numMovil = edtNumMovil.getText().toString().trim();
        dominio = edtDominio.getText().toString().trim();
        conductaAgentes = edtConductaAgentes.getText().toString().trim();

        //HECHO POLICIAL
        diaHecho = tvDateHecho.getText().toString().trim();
        horaHecho = tvHoraHecho.getText().toString().trim();
        ubicacionHecho = edtDireccionHecho.getText().toString() + " " + edtBarrioHecho.getText().toString() + " " + edtCiudadHecho.getText().toString();
        cuantosAcompañan = edtCuantosAcompañan.getText().toString().trim();
        cualLugar = edtCualLugar.getText().toString().trim();
        provinciaHecho = edtProvinciaHecho.getText().toString();
        paisHecho = edtPaisHecho.getText().toString();

        //MODALIDAD DE DETENCION
        posicionDetenido = edtPosicionDetenido.getText().toString();
        cuantoTiempoDetenido = edtCuantoTiempoDetenido.getText().toString();

        //OMISION AL ACTUAR
        mediosDeAsistencia = edtMedioAsistencia.getText().toString().trim();
        aQuienAsistencia = edtAQuien.getText().toString().trim();
        denunciaRechazada = checkeoRadioGroupConEditText(rgDenunciaTomadaSiNo, edtMotivosDenunciaRechazada, "Seleccione en denuencia rechazada");
        violentado = checkeoRadioGroupSinEditText(rgViolentadoSiNo, "Seleccione en violentado");
        denunciaFinalSeleccion(rbtSiHizoDenuncia, edtDondeDenuncia, rbtNoHizoDenuncia, edtPorQueNoDenuncia, rbtNoSabeHacerDenuncia);

        //RESULTADO_INVESTIGACION
        resultadoInvestigacion = checkeoRadioGroupSinEditText(rgEtapaDeInvestigacion, "Seleccione en resultado de investigacion");
        trabajanLosOficiales = checkeoRadioGroupSinEditText(rgOficialesTrabajanSiNo, "Seleccione si los oficiales aun trabajan");

        //TRASLADO
        traslado = checkeoRadioGroupConEditText(rgTrasladoSiNo, edtDondeTrasladaron, "Seleccione traslado");
        comisaria = checkeoRadioGroupConEditText(rgComisariaSiNo, edtCualComisaria, "Seleceione opcion en comisaria");
        esposado = checkeoRadioGroupSinEditText(rgEsposadosSiNo, "Seleccione opcion en esposado");

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
    }

    private boolean validacionRadioButtons() {
        return maltratos.isEmpty() || motivoProcedimiento.isEmpty() || traslado.isEmpty() || comisaria.isEmpty() || esposado.isEmpty() || ordenAllanamiento.isEmpty() ||
                agresionAllanamiento.isEmpty() || pertenenciasAllanamiento.isEmpty() || omisionPertenencias.isEmpty() || detenidosAllanamiento.isEmpty() ||
                posicionDetenidos.isEmpty() || esposados.isEmpty() || denunciaRechazada.isEmpty() || violentado.isEmpty() || resultadoInvestigacion.isEmpty() ||
                trabajanLosOficiales.isEmpty();
    }

    private boolean validateEntrevistador() {
        /*todo falta ingresar la fecha de entrevistador;
        tvDateEntrevista.setError("Ingrese nombre"); */
        return vGeneric(nombreEntrevistador, eNombreEntrevistador, "ingrese entrevistador") || vGeneric(apellidoEntrevistador, eApellidoEntrevistador, "ingrese apellido entrevistador")
                || vGeneric(asamblea, eAsamblea, "ingrese asamblea") || vGeneric(parentesco, edtParentesco, "ingrese parentesco");
    }

    private boolean validateVictima() {
        return vGeneric(nombreVictima, edtNombreVictima, "ingrese nombre de victima") || vGeneric(apellidoVictima, edtApellidoVictima, "ingrese apellido victima")
                || vGeneric(generoVictima, edtGeneroVictima, "ingrese genero victima") || vGeneric(edadVictima, edtEdadVictima, "ingrese edad victima")
                || vGeneric(nacionalidadVictima, edtNacionalidadVictima, "ingrese nacionalidad") || vGeneric(documentoVictima, edtDocumentoVictima, "ingrese documento victima")
                || vGeneric(direccionVictima, edtDireccionVictima, "ingrese direccion victima") || vGeneric(barrioVictima, edtBarrioVictima, "ingrese barrio victima")
                || vGeneric(telefonoVictima, edtTelefonoVictima, "ingrese telefono victima");
    }

    private boolean validateHecho() {
        //todo verificiar de otra forma diaHecho - tvDateHecho; horaHecho - tvHoraHecho
        return vGeneric(ubicacionHecho, edtDireccionHecho, "ingrese la ubicacion") || vGeneric(cuantosAcompañan, edtCuantosAcompañan, "ingrese acompañantes")
                || vGeneric(cualLugar, edtCualLugar, "ingrese lugar") || vGeneric(provinciaHecho, edtProvinciaHecho, "ingrese provincia")
                || vGeneric(paisHecho, edtPaisHecho, "ingrese pais hecho");
    }

    private boolean vGeneric(String string, EditText txt, String mensaje) {
        if (string.isEmpty()) {
            txt.setError(mensaje);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void chooseInicio() {
        Boolean admin = Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser());
        Boolean habilitado = Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser());
        if (admin && habilitado) {
            startActivity(new Intent(ReporteActivity.this, AdminInicioActivity.class));
            finish();
        }
        if (!admin && habilitado) {
            startActivity(new Intent(ReporteActivity.this, InicioActivity.class));
        }
    }

    public DatePickerDialog.OnDateSetListener crearListener(final TextView tvDate) {
        DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                tvDate.setText(date);
            }
        };
        return DateSetListener;
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

    private void denunciaFinalSeleccion(final RadioButton rbtSi, final EditText edtDonde, final RadioButton rbtNo, final EditText porQueNo, final RadioButton noSabe) {

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

    public void limpiarEditText(EditText edtText) {
        edtText.setText("");
        //TODO que el editext pierda el focus al limpiarse.
        //edtText.setFocus;
    }

    private String checkeoRadioGroupConEditText(RadioGroup grupoGroup, EditText text, String mensajeError) {
        final RadioButton algo;

        if (seSeleccionoAlmenosUnoEn(grupoGroup)) {
            algo = findViewById(grupoGroup.getCheckedRadioButtonId());

            if (algo.getText().toString().equals("Otro:") || algo.getText().toString().equals("Si")) {
                if (text.getText().toString().isEmpty()) {
                    makeTxt("Seleccionó la opcion " + algo.getText().toString() + ", especifique");
                    text.setError("completar");
                    return "";
                } else {
                    return text.getText().toString().trim();
                }
            } else {
                return algo.getText().toString().trim();
            }
        } else {
            makeTxt(mensajeError);
            return "";
        }
    }

    private String checkeoRadioGroupSinEditText(RadioGroup radioGroup, String mensajeError) {
        final RadioButton algo;

        if (this.seSeleccionoAlmenosUnoEn(radioGroup)) {
            algo = findViewById(radioGroup.getCheckedRadioButtonId());
            return algo.getText().toString().trim();
        } else {
            makeTxt(mensajeError);
            return "";
        }
    }

    private boolean seSeleccionoAlmenosUnoEn(RadioGroup grupo) {
        //int id = grupo.getCheckedRadioButtonId();
        return grupo.getCheckedRadioButtonId() != -1;
    }

    private void makeTxt(String mensaje) {
        Toast.makeText(ReporteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
