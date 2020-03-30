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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.Check;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.SharedPrefManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReporteActivity extends MasterClass {
    private String fechaCreacionReporte, horaCreacionReporte;
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

    private Check check = new Check();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        progressDialog = new ProgressDialog(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

        edtNombreEntrevistador.setText(SharedPrefManager.getInstance(this).getKeyName());
        edtApellidoEntrevistador.setText(SharedPrefManager.getInstance(this).getKeySurname());
        edtAsamblea.setText(SharedPrefManager.getInstance(this).getKeyAsamblea());

        tvDateEntrevista.setText(DateDefinido.getFechaDispositivo());
        tvDateEntrevista.setOnClickListener(view -> {
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
        });

        tvDateHecho.setOnClickListener(view -> {
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
        });


        tvHoraHecho.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(
                    ReporteActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (view1, hourOfDay, minute) -> tvHoraHecho.setText(hourOfDay + ":" + minute), 0, 0, true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        guardar.setOnClickListener(view -> enviarReporte());

        cancelar.setOnClickListener(view -> onBackPressed());

        //hace que el teclado se oculte cuando presionas en otra parte visual del mismo layout
        constraintLayout.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //vuelve al activity anterior
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de reporte?")
                .setCancelable(true)
                .setPositiveButton("Si", (dialog, which) -> {
                    ReporteActivity.this.finish();
                    return;
                })

                .setNegativeButton("No", (dialog, i) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void enviarReporte() {
        inicializarStringVariables();

        if (checkVariables().isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_REPORTE));
        } else {
            makeTxt("Revise los campos", ReporteActivity.this);
        }
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("fechaReporte", fechaCreacionReporte);
        parametros.put("horaReporte", horaCreacionReporte);
        parametros.put("reporte", this.jsonObject().toString());
        return parametros;
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
            jsonObject.put("usu_cuantos_acompanian", cuantosAcompañan);
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
        fechaCreacionReporte = DateDefinido.getFechaDispositivo();
        horaCreacionReporte = DateDefinido.getHoraDispositivo();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Boolean> checkVariables() {
        //ALLANAMIENTO
        check.addListToCheck(check.isStringEmpty(ordenAllanamiento, "Seleccione opcion en Orden de Allanamiento", this));
        check.addListToCheck(check.isStringEmpty(agresionAllanamiento, "Seleccione agresion en Allanamiento a Domicilio", this));
        check.addListToCheck(check.isStringEmpty(pertenenciasAllanamiento, "Seleccione opcion en pertenencias Allanamiento", this));
        check.addListToCheck(check.isStringEmpty(omisionPertenencias, "Seleccione opcion en omision de pertenencias", this));
        check.addListToCheck(check.isStringEmpty(detenidosAllanamiento, "Seleccione opcion en personas detenidas", this));
        check.addListToCheck(check.isStringEmpty(duracionAllanamiento, edtTiempoAllanamiento, "Ingrese tiempo detenido"));
        check.addListToCheck(check.isStringEmpty(posicionDetenidos, "Seleccione opcion en posicion fisica", this));
        check.addListToCheck(check.isStringEmpty(esposados, "Selecione opcion en esposados", this));
        //CARACTERISTICAS DE PROCEDIMIENTO
        check.addListToCheck(check.isStringEmpty(motivoProcedimiento, "Seleccione procedimiento", this));
        check.addListToCheck(check.isStringEmpty(maltratos, "Seleccione opcion en maltratos", this));
        check.addListToCheck(check.isStringEmpty(lesiones, "Seleccione opcion en Lesiones y/ complete", this));
        //ENTREVISTADOR
        check.addListToCheck(check.isStringEmpty(parentesco, edtParentesco, "ingrese parentesco"));
        check.addListToCheck(check.isStringEmpty(nombreEntrevistador, edtNombreEntrevistador, "ingrese entrevistador"));
        check.addListToCheck(check.isStringEmpty(apellidoEntrevistador, edtApellidoEntrevistador, "ingrese apellido entrevistador"));
        check.addListToCheck(check.isStringEmpty(asamblea, edtAsamblea, "ingrese asamblea"));
        //FUERZAS INTERVINIENTES
        check.addListToCheck(check.isStringEmpty(fuerzasIntervinientes, edtFuerzasIntervinientes, "Ingrese fuerzas intervinientes"));
        check.addListToCheck(check.isStringEmpty(cantidadAgentes, edtCantidadAgentes, "Ingrese cantidad de agentes"));
        check.addListToCheck(check.isStringEmpty(nombresAgentes, edtNombresAgentes, "Ingrese nombre agente"));
        check.addListToCheck(check.isStringEmpty(apodos, edtApodos, "Ingrese apodos"));
        check.addListToCheck(check.isStringEmpty(cantidadVehiculos, edtCantidadVehiculos, "Ingrese cantidad de vehiculos"));
        check.addListToCheck(check.isStringEmpty(numMovil, edtNumMovil, "Ingrese numero movil"));
        check.addListToCheck(check.isStringEmpty(dominio, edtDominio, "Ingrese dominio"));
        check.addListToCheck(check.isStringEmpty(conductaAgentes, edtConductaAgentes, "Ingrese conducta agentes"));
        //HECHO POLICIAL
        check.addListToCheck(check.isStringEmpty(cuantosAcompañan, edtCuantosAcompañan, "Ingrese cuantos acompañan"));
        check.addListToCheck(check.isStringEmpty(cualLugar, edtCualLugar, "Ingrese cual lugar"));
        check.addListToCheck(check.isStringEmpty(provinciaHecho, edtProvinciaHecho, "Ingrese provincia"));
        check.addListToCheck(check.isStringEmpty(paisHecho, edtPaisHecho, "Ingrese pais"));
        check.addListToCheck(check.isStringEmpty(direccionHecho, edtDireccionHecho, "Ingrese direccion"));
        check.addListToCheck(check.isStringEmpty(barrioHecho, edtBarrioHecho, "Ingrese barrio"));
        check.addListToCheck(check.isStringEmpty(diaHecho, tvDateHecho, "Ingrese Fecha en Descripcion del Hecho", this));
        check.addListToCheck(check.isStringEmpty(horaHecho, tvHoraHecho, "Ingrese Hora en Descripcion del Hecho", this));
        //MODALIDAD DE DETENCION
        check.addListToCheck(check.isStringEmpty(posicionDetenido, edtPosicionDetenido, "Ingrese posicion detenido"));
        check.addListToCheck(check.isStringEmpty(cuantoTiempoDetenido, edtCuantoTiempoDetenido, "Ingrese cuanto tiempo detenido"));
        //OMISION AL ACTUAL
        check.addListToCheck(check.isStringEmpty(mediosDeAsistencia, edtMedioAsistencia, "Ingrese medios de asistencia"));
        check.addListToCheck(check.isStringEmpty(aQuienAsistencia, edtAQuien, "Ingrese quien lo asistio"));
        check.addListToCheck(check.isStringEmpty(denunciaRechazada, "Selecione en denuncia", this));
        check.addListToCheck(check.isStringEmpty(violentado, "Seleccione si fue agredido, en omision", this));
        check.addListToCheck(check.isStringEmpty(denunciaFinal, "Seleccione en si hizo una denuncia", this));
        //RESULTADO DE INVESTIGACION
        check.addListToCheck(check.isStringEmpty(resultadoInvestigacion, "Selecione resultado de investigacion", this));
        check.addListToCheck(check.isStringEmpty(trabajanLosOficiales, "Seleccione si trabajan los oficiales", this));
        //TRASLADO
        check.addListToCheck(check.isStringEmpty(traslado, "Selecione en traslado", this));
        check.addListToCheck(check.isStringEmpty(comisaria, "Selecione en comisaria", this));
        check.addListToCheck(check.isStringEmpty(esposado, "Seleccione en esposado", this));
        //VICTIMA
        check.addListToCheck(check.isStringEmpty(nombreVictima, edtNombreVictima, "ingrese nombre de victima"));
        check.addListToCheck(check.isStringEmpty(apellidoVictima, edtApellidoVictima, "ingrese apellido victima"));
        check.addListToCheck(check.isStringEmpty(generoVictima, edtGeneroVictima, "ingrese genero victima"));
        check.addListToCheck(check.isStringEmpty(edadVictima, edtEdadVictima, "ingrese edad victima"));
        check.addListToCheck(check.isStringEmpty(nacionalidadVictima, edtNacionalidadVictima, "ingrese nacionalidad"));
        check.addListToCheck(check.isStringEmpty(documentoVictima, edtDocumentoVictima, "ingrese documento victima"));
        check.addListToCheck(check.isStringEmpty(direccionVictima, edtDireccionVictima, "ingrese direccion victima"));
        check.addListToCheck(check.isStringEmpty(barrioVictima, edtBarrioVictima, "ingrese barrio victima"));
        check.addListToCheck(check.isStringEmpty(telefonoVictima, edtTelefonoVictima, "ingrese telefono victima"));
        return check.finalValidation();
    }

    @Override
    protected void responseConexion(String response) {
        boolean validate = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            makeTxt(jsonObject.getString("message"), ReporteActivity.this);
            validate = jsonObject.getBoolean("validate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (validate) {
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
            finish();
        }
    }

    public DatePickerDialog.OnDateSetListener listenerFecha(final TextView tvDate) {
        return (view, year, month, dayOfMonth) -> tvDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
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

    private String chooseSeleccion(EditText text, RadioButton algo) {
        if (text.getText().toString().isEmpty()) {
            makeTxt("Seleccionó la opcion " + algo.getText().toString() + ", especifique", ReporteActivity.this);
            text.setError("completar");
            return "";
        } else {
            text.requestFocus();
            return algo.getText().toString().trim() + ": " + text.getText().toString().trim();
        }
    }
}
