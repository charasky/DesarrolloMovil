package org.lapoderosa.app.normal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.lapoderosa.app.MasterClass;
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
    private EditText edtDireccionHecho, edtBarrioHecho, edtCiudadHecho, edtProvinciaHecho, edtCuantosAcompañan, edtCualLugar;
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
        //!validateVictima() && !validateEntEntrevistador() &&
        if (validacionRadioButtons()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.URL_REPORTE));
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        //ALLANAMIENTO
        parametros.put("usu_orden_allanamiento", ordenAllanamiento);
        parametros.put("usu_agresion_allanamiento", agresionAllanamiento);
        parametros.put("usu_pertenencias_allanamiento", pertenenciasAllanamiento);
        parametros.put("usu_omision_pertenencias", omisionPertenencias);
        parametros.put("usu_detenidos_allanamiento", detenidosAllanamiento);
        parametros.put("usu_duracion_allanamiento", duracionAllanamiento);
        //todo falta agregar a php y tabla usu_posicion_allanamiento
        parametros.put("usu_posicion_allanamiento", posicionDetenidos);
        parametros.put("usu_esposados", esposados);
        //CARACTERISTICA DEL PROCEDIMIENTO
        parametros.put("usu_motivo_procedimiento", motivoProcedimiento);
        parametros.put("usu_maltratos", maltratos);
        parametros.put("usu_lesiones", lesiones);
        //Entrevistado
        parametros.put("usu_parentesco_entrevistado", parentesco);
        //Entrevistador
        parametros.put("usu_nombre", nombreEntrevistador);
        parametros.put("usu_apellido", apellidoEntrevistador);
        parametros.put("usu_asamblea", asamblea);
        parametros.put("usu_fecha", fecha);
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
        //Hecho policial
        parametros.put("usu_dia_hecho", diaHecho);
        parametros.put("usu_hora_hecho", horaHecho);
        parametros.put("usu_ubicacion_hecho", ubicacionHecho);
        parametros.put("usu_cuantos_acompañan", cuantosAcompañan);
        parametros.put("usu_cual_lugar", cualLugar);
        //MODALIDAD DE DETENCION
        parametros.put("usu_posicion_detenido", posicionDetenido);
        parametros.put("usu_tiempo_detenido", cuantoTiempoDetenido);
        //OMISION AL ACTUAR
        parametros.put("usu_medios_de_asistencia", mediosDeAsistencia);
        parametros.put("usu_a_quien_asistencia", aQuienAsistencia);
        parametros.put("usu_denuncia_rechazada", denunciaRechazada);
        parametros.put("usu_violentado", violentado);
        parametros.put("usu_denuncia_final", denunciaFinal);
        //RESULTADO INVESTIGACION
        parametros.put("usu_resultado_investigacion", resultadoInvestigacion);
        parametros.put("usu_trabajan_los_oficiales", trabajanLosOficiales);
        //TRASLADO
        parametros.put("usu_traslado", traslado);
        parametros.put("usu_comisaria", comisaria);
        parametros.put("usu_esposado", esposado);
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
        }

    @Override
    protected void inicializarStringVariables() {
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
            lesiones += edtFisica.getText().toString();
        }
        if (!edtPsiquica.getText().toString().isEmpty()) {
            lesiones += edtPsiquica.getText().toString();
        }
        if (!edtInsomnioPanico.getText().toString().isEmpty()) {
            lesiones += edtInsomnioPanico.getText().toString();
        }
        if (!edtOtrosMiedos.getText().toString().isEmpty()) {
            lesiones += edtOtrosMiedos.getText().toString();
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
        ubicacionHecho = edtDireccionHecho.getText().toString() + " " + edtBarrioHecho.getText().toString() + " " + edtCiudadHecho.getText().toString() + " " + edtProvinciaHecho.getText().toString();
        cuantosAcompañan = edtCuantosAcompañan.getText().toString().trim();
        cualLugar = edtCualLugar.getText().toString().trim();

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

    }

    private void chooseInicio() {
        Boolean admin = Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser());
        Boolean habilitado = Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser());
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
        int id = grupo.getCheckedRadioButtonId();
        return id != -1;
    }

    private void makeTxt(String mensaje) {
        Toast.makeText(ReporteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
