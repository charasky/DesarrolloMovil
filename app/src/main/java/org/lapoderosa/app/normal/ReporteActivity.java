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
import android.os.Build;
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

import androidx.annotation.RequiresApi;
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
import java.util.Objects;

//todo verificar que los campos de strings no esten vacios
public class ReporteActivity extends MasterClass {
    private Date date = new Date();
    private String fechaCreacionReporte, horaCreacionReporte;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateHourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private TextView tvDateEntrevista, tvDateHecho, tvHoraHecho;
    private Button guardar, cancelar;
    //ALLANAMIENTO
    private String ordenAllanamiento, agresionAllanamiento, pertenenciasAllanamiento, omisionPertenencias, detenidosAllanamiento, duracionAllanamiento, posicionDetenidos, esposados;
    //CARACTERISTICAS DEL PROCEDIMIENTO
    private String motivoProcedimiento, maltratos, lesiones;
    //ENTREVISTADO
    private String parentesco;
    //ENTREVISTADOR
    private String nombreEntrevistador, apellidoEntrevistador, asamblea, fecha;
    //FUERZAS INTERVINIENTES
    private String fuerzasIntervinientes, cantidadAgentes, nombresAgentes, apodos, cantidadVehiculos, numMovil, dominio, conductaAgentes;
    //HECHO POLICIAL todo revisar direccion barrio ciudad
    //ubicacionHecho
    private String direccionHecho, barrioHecho, diaHecho, horaHecho, cuantosAcompañan, cualLugar, provinciaHecho, paisHecho;
    //MODALIDAD DE DETENCION
    private String posicionDetenido, cuantoTiempoDetenido;
    //OMISION AL ACTUAR
    private String mediosDeAsistencia, aQuienAsistencia, denunciaRechazada, violentado, denunciaFinal;
    //RESULTADO DE LA INVESTIGACION
    private String resultadoInvestigacion, trabajanLosOficiales;
    //TRASLADO
    private String traslado, comisaria, esposado;
    //VICTIMA
    private String nombreVictima, apellidoVictima, generoVictima, edadVictima, nacionalidadVictima, documentoVictima, direccionVictima, barrioVictima, telefonoVictima;

    //**************//
    //ALLANAMIENTO
    private EditText edtCualesAgresionesDomicilio, edtCualesPertenencias, edtCualesOmitieron, edtCuantosDetenidos, edtTiempoAllanamiento, edtOtraPosicion;
    //ENTREVISTADOR
    private EditText edtNombreEntrevistador, edtApellidoEntrevistador, edtAsamblea;
    //ENTREVISTADO
    private EditText edtParentesco;
    //FUERZAS INTERVINIENTES
    private EditText edtFuerzasIntervinientes, edtCantidadAgentes, edtNombresAgentes, edtApodos, edtCantidadVehiculos, edtNumMovil, edtDominio, edtConductaAgentes;
    //HECHO POLICIAL
    private EditText edtDireccionHecho, edtBarrioHecho, edtProvinciaHecho, edtPaisHecho, edtCuantosAcompañan, edtCualLugar;
    //MODALIDAD DE DETENCION
    private EditText edtPosicionDetenido, edtCuantoTiempoDetenido, edtDondeTrasladaron, edtCualComisaria;
    //OMISION AL ACTUAR
    private EditText edtMedioAsistencia, edtAQuien, edtMotivosDenunciaRechazada;
    //MALTRATOS
    private EditText edtOtraAgresion;
    //LESIONES
    private EditText edtAclararLesiones;
    //DENUNCIA
    private EditText edtDondeDenuncia, edtPorQueNoDenuncia;
    //RESULTADO DE LA INVESTIGACION
    private RadioGroup rgDenuncia;
    //VICTIMA
    private EditText edtNombreVictima, edtApellidoVictima, edtGeneroVictima, edtEdadVictima, edtNacionalidadVictima, edtDocumentoVictima, edtDireccionVictima, edtBarrioVictima, edtTelefonoVictima;

    private ConstraintLayout constraintLayout;

    private RadioGroup rgProcesamiento, rgMalosTratosAgresiones, rgTrasladoSiNo, rgComisariaSiNo, rgEsposadosSiNo, rgOrdenAllanamientoSiNo,
            rgAgresionDomicilioSiNo, rgPertenenciasRobadasSiNo, rgOmitieronPertenenciasSiNo, rgPersonasDetenidasSiNo, rgPosicionFisicaEleccion, rgEsposadosAllanamientoSiNo,
            rgDenunciaTomadaSiNo, rgViolentadoSiNo, rgEtapaDeInvestigacion, rgOficialesTrabajanSiNo, rgLesionesSiNo;

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

        rgLesionesSiNo = findViewById(R.id.rgLesionesSiNo);
        edtAclararLesiones = findViewById(R.id.edtAclararLesiones);

        //ENTREVISTADO
        edtParentesco = findViewById(R.id.edtParentesco);

        //ENTREVISTADOR
        edtNombreEntrevistador = findViewById(R.id.edtNombreEntrevistador);
        edtApellidoEntrevistador = findViewById(R.id.edtApellidoEntrevistador);
        edtAsamblea = findViewById(R.id.edtAsamblea);
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
        rgDenuncia = findViewById(R.id.rgDenuncia);
        edtDondeDenuncia = findViewById(R.id.edtDondeDenuncia);
        edtPorQueNoDenuncia = findViewById(R.id.edtPorQueNoDenuncia);

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

        tvDateEntrevista.setText(dateFormat.format(date));
        tvDateEntrevista.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        listenerFecha(tvDateEntrevista),
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        tvDateHecho.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        listenerFecha(tvDateHecho),
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        tvHoraHecho.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(
                        ReporteActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHoraHecho.setText(hourOfDay + ":" + minute);
                            }
                        }, 0, 0, true);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(v.getWindowToken(), 0);
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

        if (verificaciones()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_REPORTE));
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("fechaReporte", fechaCreacionReporte);
        parametros.put("horaReporte", horaCreacionReporte);
        parametros.put("reporte", this.jsonObject().toString());
        //parametros.put("reporte", new JSONObject(this.mapaDeVariables()).toString());
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
            jsonObject.put("usu_cuantos_acompañan", cuantosAcompañan);
            jsonObject.put("usu_cual_lugar", cualLugar);
            jsonObject.put("usu_provincia_hecho", provinciaHecho);
            jsonObject.put("usu_pais_hecho", paisHecho);
            jsonObject.put("usu_direccion_hecho", direccionHecho);
            jsonObject.put("usu_barrio_hecho", barrioHecho);
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
        ordenAllanamiento = checkRadioGroup(rgOrdenAllanamientoSiNo);
        agresionAllanamiento = checkRadioGroup(rgAgresionDomicilioSiNo, edtCualesAgresionesDomicilio);
        pertenenciasAllanamiento = checkRadioGroup(rgPertenenciasRobadasSiNo, edtCualesPertenencias);
        omisionPertenencias = checkRadioGroup(rgOmitieronPertenenciasSiNo, edtCualesOmitieron);
        detenidosAllanamiento = checkRadioGroup(rgPersonasDetenidasSiNo, edtCuantosDetenidos);
        duracionAllanamiento = edtTiempoAllanamiento.getText().toString();
        posicionDetenidos = checkRadioGroup(rgPosicionFisicaEleccion, edtOtraPosicion);
        esposados = checkRadioGroup(rgEsposadosAllanamientoSiNo);

        //CARACTERISTICAS_PROCESAMIENTO
        motivoProcedimiento = checkRadioGroup(rgProcesamiento);
        maltratos = checkRadioGroup(rgMalosTratosAgresiones, edtOtraAgresion);
        lesiones = checkRadioGroup(rgLesionesSiNo, edtAclararLesiones);

        //ENTREVISTADO
        parentesco = edtParentesco.getText().toString().trim();

        //ENTREVISTADOR
        nombreEntrevistador = edtNombreEntrevistador.getText().toString().trim();
        apellidoEntrevistador = edtApellidoEntrevistador.getText().toString().trim();
        asamblea = edtAsamblea.getText().toString().trim();
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
        direccionHecho = edtDireccionHecho.getText().toString().trim();
        barrioHecho = edtBarrioHecho.getText().toString().trim();
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
        denunciaRechazada = checkRadioGroup(rgDenunciaTomadaSiNo, edtMotivosDenunciaRechazada);
        violentado = checkRadioGroup(rgViolentadoSiNo);
        denunciaFinal = checkRadioGroup(rgDenuncia, edtDondeDenuncia, edtPorQueNoDenuncia);

        //RESULTADO_INVESTIGACION
        resultadoInvestigacion = checkRadioGroup(rgEtapaDeInvestigacion);
        trabajanLosOficiales = checkRadioGroup(rgOficialesTrabajanSiNo);

        //TRASLADO
        traslado = checkRadioGroup(rgTrasladoSiNo, edtDondeTrasladaron);
        comisaria = checkRadioGroup(rgComisariaSiNo, edtCualComisaria);
        esposado = checkRadioGroup(rgEsposadosSiNo);

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

    private boolean verificaciones(){
        return !vEntrevistador() && !vVictima() && !vHechoPolicial() && !vfuerzasIntervinientes()
                && !vModalidadDetencion() && !vOmisionActuar() && !vTraslado()
                && !vAllanamiento() && !vResultadoInvestigacion();
    }

    private boolean vAllanamiento() {
        return vGeneric(ordenAllanamiento, "Seleccione opcion en Orden de Allanamiento") ||
                vGeneric(agresionAllanamiento, "Seleccione agresion en Allanamiento a Domicilio") ||
                vGeneric(pertenenciasAllanamiento, "Seleccione opcion en pertenencias Allanamiento") ||
                vGeneric(omisionPertenencias, "Seleccione opcion en omision de pertenencias") ||
                vGeneric(detenidosAllanamiento, "Seleccione opcion en personas detenidas") ||
                vGeneric(duracionAllanamiento, edtTiempoAllanamiento, "Ingrese tiempo detenido") ||
                vGeneric(posicionDetenidos, "Seleccione opcion en posicion fisica") ||
                vGeneric(esposados, "Selecione opcion en esposados");
    }

    private boolean vCaracteristicasProcedimiento() {
        return vGeneric(motivoProcedimiento, "Seleccione procedimiento") ||
                vGeneric(maltratos, "Seleccione opcion en maltratos") ||
                vGeneric(lesiones, "Seleccione opcion en Lesiones y/ complete");
    }

    private boolean vEntrevistador() {
        //la fecha de entrevistador ya viene por default completa si no la rellenan
        return vGeneric(parentesco, edtParentesco, "ingrese parentesco") ||
                vGeneric(nombreEntrevistador, edtNombreEntrevistador, "ingrese entrevistador") ||
                vGeneric(apellidoEntrevistador, edtApellidoEntrevistador, "ingrese apellido entrevistador") ||
                vGeneric(asamblea, edtAsamblea, "ingrese asamblea");
    }

    private boolean vfuerzasIntervinientes() {
        return vGeneric(fuerzasIntervinientes, edtFuerzasIntervinientes, "Ingrese fuerzas intervinientes") ||
                vGeneric(cantidadAgentes, edtCantidadAgentes, "Ingrese cantidad de agentes") ||
                vGeneric(nombresAgentes, edtNombresAgentes, "Ingrese nombre agente") ||
                vGeneric(apodos, edtApodos, "Ingrese apodos") ||
                vGeneric(cantidadVehiculos, edtCantidadVehiculos, "Ingrese cantidad de vehiculos") ||
                vGeneric(numMovil, edtNumMovil, "Ingrese numero movil") ||
                vGeneric(dominio, edtDominio, "Ingrese dominio") ||
                vGeneric(conductaAgentes, edtConductaAgentes, "Ingrese conducta agentes");
    }

    private boolean vHechoPolicial() {
        return vGeneric(cuantosAcompañan, edtCuantosAcompañan, "Ingrese cuantos acompañan") ||
                vGeneric(cualLugar, edtCualLugar, "Ingrese cual lugar") ||
                vGeneric(provinciaHecho, edtProvinciaHecho, "Ingrese provincia") ||
                vGeneric(paisHecho, edtPaisHecho, "Ingrese pais") ||
                vGeneric(direccionHecho, edtDireccionHecho, "Ingrese direccion") ||
                vGeneric(barrioHecho, edtBarrioHecho, "Ingrese barrio") ||
                vGeneric(diaHecho, "Ingrese Fecha en Descripcion del Hecho") ||
                vGeneric(horaHecho, "Ingrese Hora en Descripcion del Hecho") ;
    }

    private boolean vModalidadDetencion() {
        return vGeneric(posicionDetenido, edtPosicionDetenido, "Ingrese posicion detenido") ||
                vGeneric(cuantoTiempoDetenido, edtCuantoTiempoDetenido, "Ingrese cuanto tiempo detenido");
    }

    private boolean vOmisionActuar() {
        return vGeneric(mediosDeAsistencia, edtMedioAsistencia, "Ingrese medios de asistencia") ||
                vGeneric(aQuienAsistencia, edtAQuien, "Ingrese quien lo asistio") ||
                vGeneric(denunciaRechazada, "Selecione en denuncia") ||
                vGeneric(violentado, "Seleccione si fue agredido, en omision") ||
                vGeneric(denunciaFinal, "Seleccione en si hizo una denuncia");
    }

    private boolean vResultadoInvestigacion() {
        return vGeneric(resultadoInvestigacion, "Selecione resultado de investigacion") ||
                vGeneric(trabajanLosOficiales, "Seleccione si trabajan los oficiales");
    }

    private boolean vTraslado() {
        return vGeneric(traslado, "Selecione en traslado") ||
                vGeneric(comisaria, "Selecione en comisaria") ||
                vGeneric(esposado, "Seleccione en esposado");
    }

    private boolean vVictima() {
        return vGeneric(nombreVictima, edtNombreVictima, "ingrese nombre de victima") ||
                vGeneric(apellidoVictima, edtApellidoVictima, "ingrese apellido victima") ||
                vGeneric(generoVictima, edtGeneroVictima, "ingrese genero victima") ||
                vGeneric(edadVictima, edtEdadVictima, "ingrese edad victima") ||
                vGeneric(nacionalidadVictima, edtNacionalidadVictima, "ingrese nacionalidad") ||
                vGeneric(documentoVictima, edtDocumentoVictima, "ingrese documento victima") ||
                vGeneric(direccionVictima, edtDireccionVictima, "ingrese direccion victima") ||
                vGeneric(barrioVictima, edtBarrioVictima, "ingrese barrio victima") ||
                vGeneric(telefonoVictima, edtTelefonoVictima, "ingrese telefono victima");
    }

    private boolean vGeneric(String string, EditText txt, String mensaje) {
        if (string.isEmpty()) {
            txt.setError(mensaje);
            return false;
        }
        return true;
    }

    private boolean vGeneric(String string, String mensaje) {
        if (string.equals("dd/mm/yyyy") || string.equals("HH:mm") || string.isEmpty()) {
            makeTxt(mensaje);
            return false;
        }
        return true;
    }

    @Override
    protected void responseConexion(String response) {
        boolean validate = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            validate = jsonObject.getBoolean("validate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(validate){
            chooseInicio();
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

    public DatePickerDialog.OnDateSetListener listenerFecha(final TextView tvDate) {
        return new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        };
    }

    private String checkRadioGroup(RadioGroup grupoGroup, EditText text) {
        final RadioButton algo;

        if (seSeleccionoAlmenosUnoEn(grupoGroup)) {
            algo = findViewById(grupoGroup.getCheckedRadioButtonId());

            if (algo.getText().toString().equals("Otro:") || algo.getText().toString().equals("Si")) {
                return this.chooseSeleccion(text, algo);
            } else {
                return algo.getText().toString().trim();
            }
        } else {
            return "";
        }
    }

    private String checkRadioGroup(RadioGroup grupoGroup, EditText text, EditText text2) {
        final RadioButton algo;

        if (seSeleccionoAlmenosUnoEn(grupoGroup)) {
            algo = findViewById(grupoGroup.getCheckedRadioButtonId());

            if (algo.getText().toString().equals("Si")) {
                text2.setText("");
                return this.chooseSeleccion(text, algo);
            } else if (algo.getText().toString().equals("No")) {
                text.setText("");
                return this.chooseSeleccion(text2, algo);
            } else {
                return algo.getText().toString().trim();
            }
        } else {
            return "";
        }
    }

    private String checkRadioGroup(RadioGroup radioGroup) {
        final RadioButton algo;

        if (this.seSeleccionoAlmenosUnoEn(radioGroup)) {
            algo = findViewById(radioGroup.getCheckedRadioButtonId());
            return algo.getText().toString().trim();
        } else {
            return "";
        }
    }

    private boolean seSeleccionoAlmenosUnoEn(RadioGroup grupo) {
        return grupo.getCheckedRadioButtonId() != -1;
    }

    private void makeTxt(String mensaje) {
        Toast.makeText(ReporteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private String chooseSeleccion(EditText text, RadioButton algo) {
        if (text.getText().toString().isEmpty()) {
            makeTxt("Seleccionó la opcion " + algo.getText().toString() + ", especifique");
            text.setError("completar");
            return "";
        } else {
            text.requestFocus();
            return algo.getText().toString().trim() + ": " + text.getText().toString().trim();
        }
    }
}
