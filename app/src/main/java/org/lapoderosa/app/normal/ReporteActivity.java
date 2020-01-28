package org.lapoderosa.app.normal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.lapoderosa.app.MasterClass;

import java.util.Calendar;
import java.util.Map;

public class ReporteActivity extends MasterClass {
    private static final String TAG = "ReporteActivity";
    private TextView tvDateEntrevista,tvDateHecho,tvHoraHecho;
    private DatePickerDialog.OnDateSetListener m1DateSetListener;
    private DatePickerDialog.OnDateSetListener m2DateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button guardar,cancelar;
    private int mHour, mMinute;
    //ENTREVISTADOR
    private String nombreEntrevistador,apellidoEntrevistador,asamblea,fecha;
    //ENTREVISTADO
    private String parentesco;
    //VICTIMA
    private String nombreVictima,apellidoVictima,generoVictima,edadVictima,nacionalidadVictima,documentoVictima,direccionVictima,barrioVictima,telefonoVictima;
    //HECHO POLICIAL
    private String direccionHecho,barrioHecho,ciudadHecho,provinciaHecho,cuantosAcompañan,cualLugar,diaHecho,horaHecho;
    private String ubicacionHecho;
    //FUERZAS INTERVINIENTES
    private String fuerzasIntervinientes,cantidadAgentes,nombresAgentes,apodos,cantidadVehiculos,numMovil,dominio,conductaAgentes;
    //CARACTERISTICAS DEL PROCEDIMIENTO
    private String motivoProcedimiento;
    //MALTRATOS
    private String maltratos, otraAgresion;
    //LESIONES
    private String lesiones;
    //MODALIDAD DE DETENCION
    private String posicionDetenido,cuantoTiempoDetenido,traslado,comisaria,esposado;
    //ALLANAMIENTO
    private String ordenAllanamiento,agresionAllanamiento,pertenenciasAllanamiento,omisionPertenencias,detenidosAllanamiento,duracionAllanamiento,posicionDetenidos,esposados;
    //OMISION AL ACTUAR
    private String mediosDeAsistencia,aQuienAsistencia,denunciaRechazada,violentado;
    //DENUNCIA
    private String hizoDenuncia,dondeDenuncia,porQueNoDenuncia,denunciaFinal;
    //RESULTADO DE LA INVESTIGACION
    private String resultadoInvestigacion,trabajanLosOficiales;

    //**************//
    //ENTREVISTADOR
    private EditText edtNombreEntrevistador,edtApellidoEntrevistador,edtAsamblea,diaEntrevista;
    //ENTREVISTADO
    private EditText edtParentesco;
    //VICTIMA
    private EditText edtNombreVictima,edtApellidoVictima,edtGeneroVictima,edtEdadVictima,edtNacionalidadVictima,
            edtDocumentoVictima,edtDireccionVictima,edtBarrioVictima,edtTelefonoVictima;
    //HECHO POLICIAL
    private EditText edtDireccionHecho,edtBarrioHecho,edtCiudadHecho,edtProvinciaHecho,edtCuantosAcompañan,edtCualLugar;
    //FUERZAS INTERVINIENTES
    private EditText edtFuerzasIntervinientes,edtCantidadAgentes,edtNombresAgentes,edtApodos,edtCantidadVehiculos,edtNumMovil,edtDominio,edtConductaAgentes;
    //CARACTERISTICAS DEL PROCEDIMIENTO
    private RadioButton rbtDelito,rbtAveriguacion,rbtRequiza,rbtNoMotivos;
    //MALTRATOS
    private RadioButton rbtNoMaltratos,rbtVerbales,rbtFisicos,rbtAmenazas,rbtDañoPertenencias,rbtRequizaHumillante;
    private EditText edtOtraAgresion;
    //LESIONES
    private RadioButton rbtNoLesiones;
    private EditText edtFisica,edtPsiquica,edtInsomnioPanico,edtOtrosMiedos;
    //MODALIDAD DE DETENCION
    private EditText edtPosicionDetenido,edtCuantoTiempoDetenido,edtDondeTrasladaron,edtCualComisaria;
    private RadioButton rbtNoTrasladaron,rbtNoComisaria,rbtNoEsposados,rbtSiEsposados;
    //ALLANAMIENTO
    private EditText edtCualesAgresionesDomicilio,edtCualesPertenencias,edtCualesOmitieron,edtCuantosDetenidos,edtTiempoAllanamiento,edtOtraPosicion;
    private RadioButton rbtNoOrdenAllanamiento,rbtSiOrdenAllanamiento,rbtNoAgresionesDomicilio,
            rbtNoPertenenciasRobadas,rbtNoOmitieronPertenencias,rbtNoDetenidos,rbtParado,rbtArrodillado,rbtAcostado,rbtNoEsposadosAllanamiento,rbtSiEsposadosAllanamiento;
    //OMISION AL ACTUAR

    private EditText edtMedioAsistencia,edtAQuien,edtMotivosDenunciaRechazada;
    private RadioButton rbtNoDenuncia,rbtNoViolentado,rbtSiViolentado;
    //DENUNCIA

    private EditText edtDondeDenuncia,edtPorQueNoDenuncia;
    private RadioButton rbtSiHizoDenuncia,rbtNoHizoDenuncia,rbtNoSabeHacerDenuncia;
    //RESULTADO DE LA INVESTIGACION

    private RadioButton rbtCondena,rbtAbsolucion,rbtCausaEnTramite,rbtArchivo,rbtNoSabeResultado,rbtNoTrabajaMas,rbtSiTrabaja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        guardar = findViewById(R.id.btnGuardar);
        //cancelar = findViewById(R.id.btnCancelar);
        progressDialog = new ProgressDialog(this);

        //ENTREVISTADOR
        edtNombreEntrevistador = findViewById(R.id.edtNombreEntrevistador);
        edtApellidoEntrevistador = findViewById(R.id.edtApellidoEntrevistador);
        edtAsamblea = findViewById(R.id.edtAsamblea);
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
        edtBarrioVictima = findViewById(R.id.edtBarrioVictima);
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
        edtDondeTrasladaron = findViewById(R.id.edtDondeTrasladaron);

        //TRASLADO A GARITA/DEPENDENCIA

        rbtNoComisaria = findViewById(R.id.rbtNoComisaria);
        edtCualComisaria = findViewById(R.id.edtCualComisaria);

        //ESPOSADOS

        rbtNoEsposados = findViewById(R.id.rbtNoEsposados);
        rbtSiEsposados = findViewById(R.id.rbtSiEsposados);

        //ALLANAMIENTOS

        rbtNoOrdenAllanamiento = findViewById(R.id.rbtNoOrdenAllanamiento);
        rbtSiOrdenAllanamiento = findViewById(R.id.rbtSiOrdenAllanamiento);
        rbtNoAgresionesDomicilio = findViewById(R.id.rbtNoAgresionesDomicilio);
        edtCualesAgresionesDomicilio = findViewById(R.id.edtCualesAgresionesDomicilio);
        rbtNoPertenenciasRobadas = findViewById(R.id.rbtNoPertenenciasRobadas);
        edtCualesPertenencias = findViewById(R.id.edtCualesPertenencias);
        rbtNoOmitieronPertenencias = findViewById(R.id.rbtNoOmitieronPertenencias);
        edtCualesOmitieron = findViewById(R.id.edtCualesOmitieron);
        rbtNoDetenidos = findViewById(R.id.rbtNoDetenidos);
        edtCuantosDetenidos = findViewById(R.id.edtCuantosDetenidos);
        edtTiempoAllanamiento = findViewById(R.id.edtTiempoAllanamiento);
        //modalidad detencion allanamiento
        rbtParado = findViewById(R.id.rbtParado);
        rbtArrodillado = findViewById(R.id.rbtArrodillado);
        rbtAcostado = findViewById(R.id.rbtAcostado);
        edtOtraPosicion = findViewById(R.id.edtOtraPosicion);
        //esposados
        rbtNoEsposadosAllanamiento = findViewById(R.id.rbtNoEsposadosAllanamiento);
        rbtSiEsposadosAllanamiento = findViewById(R.id.rbtSiEsposadosAllanamiento);

        //OMISION AL ACTUAR

        edtMedioAsistencia = findViewById(R.id.edtMedioAsistencia);
        edtAQuien = findViewById(R.id.edtAQuien);
        rbtNoDenuncia = findViewById(R.id.rbtNoDenuncia);
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

        listenerMotivoProcedimiento(rbtDelito,rbtAveriguacion,rbtRequiza,rbtNoMotivos);

        //logica maltratos

        listenerMaltratos(rbtNoMaltratos,rbtVerbales,rbtFisicos,rbtAmenazas,rbtDañoPertenencias,rbtRequizaHumillante,edtOtraAgresion);

        //logica lesiones

        listenerLesiones(rbtNoLesiones,edtFisica,edtPsiquica,edtInsomnioPanico,edtOtrosMiedos);

        //logica traslado
        rbtYEdtTraslado(edtDondeTrasladaron,rbtNoTrasladaron);

        //COMISARIA/DEPENDENCIA

        rbtYEdtComisaria(edtCualComisaria,rbtNoComisaria);

        //ESPOSADOS

        listenerEsposado(rbtNoEsposados,rbtSiEsposados);

        //ALLANAMIENTO

        listenerOrdenAllanamiento(rbtNoOrdenAllanamiento,rbtSiOrdenAllanamiento);

        //AGRESIONES ALLANAMIENTO

        rbtYEdtAgresionAllanamiento(edtCualesAgresionesDomicilio,rbtNoAgresionesDomicilio);

        //pertenencias Domicilio
        rbtYEdtPertenenciasAllanamiento(edtCualesPertenencias,rbtNoPertenenciasRobadas);

        //omision de pertenencias

        rbtYEdtOmisionPertenencias(edtCualesOmitieron,rbtNoOmitieronPertenencias);

        //detenidos allanamiento

        rbtYEdtDetenidosAllanamiento(edtCuantosDetenidos,rbtNoDetenidos);

        //posicion detenidos allanamiento

        listenerPosicionAllanamiento(rbtParado,rbtArrodillado,rbtAcostado,edtOtraPosicion);

        //ESPOSADOS ALLANAMIENTO

        listenerEsposadosAllanamiento(rbtNoEsposadosAllanamiento,rbtSiEsposadosAllanamiento);

        //OMISION AL ACTUAR

        //logica concurrio a denuncia y no la tomaron
        listenerDenunciaRechazada(rbtNoDenuncia,edtMotivosDenunciaRechazada);

        listenerViolentado(rbtSiViolentado,rbtNoViolentado);

        //logica denuncia

        listenerDenuncia(rbtSiHizoDenuncia,edtDondeDenuncia,rbtNoHizoDenuncia,edtPorQueNoDenuncia,rbtNoSabeHacerDenuncia);

        //RESULTADO INVESTIGACION

        logicaResultadoInvestigacion(rbtCondena,rbtAbsolucion,rbtCausaEnTramite,rbtArchivo,rbtNoSabeResultado);

        //TRABAJAN LOS OFICIALES

        listenerOficiales(rbtSiTrabaja,rbtNoTrabajaMas);


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
                        ReporteActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHoraHecho.setText(String.valueOf(hourOfDay)+ " : " + String.valueOf(minute));
                            }
                        },mHour, mMinute, true);
                dialog.show();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio(getResources().getString(R.string.URL_REPORTE));
            }
        });
        /*
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(getApplicationContext(), InicioActivity.class));
                    finish();

            }
        });
        
         */

    }



    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        //Entrevistador
        parametros.put("usu_nombre",nombreEntrevistador);
        parametros.put("usu_apellido",apellidoEntrevistador);
        parametros.put("usu_asamblea",asamblea);
        parametros.put("usu_fecha",fecha);
        //Entrevistado
        parametros.put("usu_parentesco",parentesco);
        //Victima
        parametros.put("usu_nombre_victima",nombreVictima);
        parametros.put("usu_apellido_victima",apellidoVictima);
        parametros.put("usu_genero_victima",generoVictima);
        parametros.put("usu_edad_victima",edadVictima);
        parametros.put("usu_nacionalidad_victima",nacionalidadVictima);
        parametros.put("usu_documento_victima",documentoVictima);
        parametros.put("usu_direccion_victima",direccionVictima);
        parametros.put("usu_barrio_victima",barrioVictima);
        parametros.put("usu_telefono_victima",telefonoVictima);
        //Hecho policial
        parametros.put("usu_dia_hecho",diaHecho);
        parametros.put("usu_hora_hecho",horaHecho);
        parametros.put("usu_ubicacion_hecho",ubicacionHecho);
        parametros.put("usu_cuantos_acompañan",cuantosAcompañan);
        parametros.put("usu_cual_lugar",cualLugar);
        //FUERZAS INTERVINIENTES
        parametros.put("usu_fuerzas_intervinientes",fuerzasIntervinientes);
        //agentes
        parametros.put("usu_cantidad_agentes",cantidadAgentes);
        parametros.put("usu_nombres_agentes",nombresAgentes);
        parametros.put("usu_apodos",apodos);
        //vehiculos
        parametros.put("usu_cantidad_vehiculos",cantidadVehiculos);
        parametros.put("usu_num_movil",numMovil);
        parametros.put("usu_dominio",dominio);
        parametros.put("usu_conducta_agentes",conductaAgentes);
        //CARACTERISTICA DEL PROCEDIMIENTO
        parametros.put("usu_motivo_procedimiento",motivoProcedimiento);
        parametros.put("usu_maltratos",maltratos);
        parametros.put("usu_lesiones",lesiones);
        //MODALIDAD DE DETENCION
        parametros.put("usu_posicion_detenido",posicionDetenido);
        parametros.put("usu_tiempo_detenido",cuantoTiempoDetenido);
        //TRASLADO
        parametros.put("usu_traslado",traslado);
        parametros.put("usu_comisaria",comisaria);
        parametros.put("usu_esposado",esposado);
        //ALLANAMIENTO
        parametros.put("usu_orden_allanamiento",ordenAllanamiento);
        parametros.put("usu_agresion_allanamiento",agresionAllanamiento);
        parametros.put("usu_pertenencias_allanamiento",pertenenciasAllanamiento);
        parametros.put("usu_omision_pertenencias",omisionPertenencias);
        parametros.put("usu_detenidos_allanamiento",detenidosAllanamiento);
        parametros.put("usu_duracion_allanamiento",duracionAllanamiento);
        parametros.put("usu_posicion_allanamiento",posicionDetenidos);
        parametros.put("usu_esposados",esposados);
        //OMISION AL ACTUAR
        parametros.put("usu_medios_de_asistencia",mediosDeAsistencia);
        parametros.put("usu_a_quien_asistencia",aQuienAsistencia);
        parametros.put("usu_denuncia_rechazada",denunciaRechazada);
        parametros.put("usu_violentado",violentado);
        parametros.put("usu_denuncia_final",denunciaFinal);
        parametros.put("usu_resultado_investigacion",resultadoInvestigacion);
        parametros.put("usu_trabajan_los_oficiales",trabajanLosOficiales);
    }

    @Override
    protected void inicializarStringVariables() {
        //ENTREVISTADOR
        nombreEntrevistador = edtNombreEntrevistador.getText().toString();
        apellidoEntrevistador = edtApellidoEntrevistador.getText().toString();
        asamblea = edtAsamblea.getText().toString();
        fecha = tvDateEntrevista.getText().toString();
        //ENTREVISTADO
        parentesco = edtParentesco.getText().toString();
        //VICTIMA
        nombreVictima = edtNombreVictima.getText().toString();
        apellidoVictima = edtApellidoVictima.getText().toString();
        generoVictima = edtGeneroVictima.getText().toString();
        edadVictima = edtEdadVictima.getText().toString();
        nacionalidadVictima = edtNacionalidadVictima.getText().toString();
        documentoVictima = edtDocumentoVictima.getText().toString();
        direccionVictima = edtDireccionVictima.getText().toString();
        barrioVictima = edtBarrioVictima.getText().toString();
        telefonoVictima = edtTelefonoVictima.getText().toString();
        //HECHO POLICIAL
        diaHecho = tvDateHecho.getText().toString();
        horaHecho = tvHoraHecho.getText().toString();
        ubicacionHecho = edtDireccionHecho.getText().toString() + " " +edtBarrioHecho.getText().toString() + " " + edtCiudadHecho.getText().toString() + " " + edtProvinciaHecho.getText().toString();
        cuantosAcompañan = edtCuantosAcompañan.getText().toString();
        cualLugar = edtCualLugar.getText().toString();
        //FUERZAS INTERVINIENTES
        //agentes
        fuerzasIntervinientes = edtFuerzasIntervinientes.getText().toString();
        cantidadAgentes = edtCantidadAgentes.getText().toString();
        nombresAgentes = edtNombresAgentes.getText().toString();
        apodos = edtApodos.getText().toString();
        //vehiculos
        cantidadVehiculos = edtCantidadVehiculos.getText().toString();
        numMovil = edtNumMovil.getText().toString();
        dominio = edtDominio.getText().toString();
        conductaAgentes = edtConductaAgentes.getText().toString();
        //CARACTERISTICA DEL PROCEDIMIENTO
        //motivoProcedimiento
        otraAgresion = edtOtraAgresion.getText().toString();
        //lesiones
        //MODALIDAD DE DETENCION
        posicionDetenido = edtPosicionDetenido.getText().toString();
        cuantoTiempoDetenido = edtCuantoTiempoDetenido.getText().toString();
        //TRASLADO
        //traslado
        //COMISARIA
        //comisaria
        //ESPOSADOS
        //esposado
        //ordenAllanmiento
        //agresionesAllanmiento
        //pertenenciasDomicilio
        //omision de pertenencias
        //detenidos allanamiento
        duracionAllanamiento = edtTiempoAllanamiento.getText().toString();
        //esposados
        //OMISION AL ACTUAR
        mediosDeAsistencia = edtMedioAsistencia.getText().toString();
        aQuienAsistencia = edtAQuien.getText().toString();
        //denunciaRechazada
        //violentado
        //denunciaFinal
        //resultadoInvestigacion
        //trabajanLosOficiales




    }

    @Override
    protected void responseConexion(String response) {
        if (!response.isEmpty()) {
            this.volverLogin();
        } else {
            Toast.makeText( getApplicationContext(), "Verifica los campos obligatorios", Toast.LENGTH_SHORT).show();
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

    public void listenerMotivoProcedimiento(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2, final RadioButton rbt3){

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

    public void listenerMaltratos(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2,final RadioButton rbt3, final RadioButton rbt4, final RadioButton rbt5,final EditText edt){
        rbtTrue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rbt1.setChecked(false);
                    rbt2.setChecked(false);
                    rbt3.setChecked(false);
                    rbt4.setChecked(false);
                    rbt5.setChecked(false);
                    limpiarEditText(edtOtraAgresion);
                    maltratos = rbtTrue.getText().toString();
                }
            });
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbtTrue.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt2.getText().toString();
            }
        });

        rbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbtTrue.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt3.getText().toString();
            }
        });
        rbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbtTrue.setChecked(false);
                rbt5.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt4.getText().toString();
            }
        });
        rbt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbtTrue.setChecked(false);
                limpiarEditText(edtOtraAgresion);
                maltratos = rbt5.getText().toString();
            }
        });
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbt3.setChecked(false);
                rbt4.setChecked(false);
                rbt5.setChecked(false);
                rbtTrue.setChecked(false);
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


    private void listenerEsposado(final RadioButton rbt1, final RadioButton rbt2){
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

    private void listenerOrdenAllanamiento(final RadioButton rbt1, final RadioButton rbt2){
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
                ordenAllanamiento= rbt2.getText().toString();
            }
        });
    }

     private void rbtYEdtAgresionAllanamiento(final EditText edt, final RadioButton rbt) {

        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                agresionAllanamiento = rbt.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                agresionAllanamiento = edt.getText().toString();
            }
        });

     }

     private void rbtYEdtTraslado(final EditText edt, final RadioButton rbt){
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                traslado = rbt.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                traslado = edt.getText().toString();
            }
        });
     }

     private void rbtYEdtComisaria(final EditText edt, final RadioButton rbt){
         rbt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 limpiarEditText(edt);
                 comisaria = rbt.getText().toString();
             }
         });

         edt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 rbt.setChecked(false);
                 comisaria = edt.getText().toString();
             }
         });
     }

    private void rbtYEdtPertenenciasAllanamiento(final EditText edt, final RadioButton rbt){
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                pertenenciasAllanamiento = rbt.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                pertenenciasAllanamiento = edt.getText().toString();
            }
        });
    }

    private void rbtYEdtOmisionPertenencias(final EditText edt, final RadioButton rbt){
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                omisionPertenencias = rbt.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                omisionPertenencias = edt.getText().toString();
            }
        });
    }

    private void rbtYEdtDetenidosAllanamiento(final EditText edt, final RadioButton rbt){
        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                detenidosAllanamiento = rbt.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt.setChecked(false);
                detenidosAllanamiento = edt.getText().toString();
            }
        });
    }

    private void listenerEsposadosAllanamiento(final RadioButton rbt1, final RadioButton rbt2){
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

    private void listenerPosicionAllanamiento(final RadioButton rbtTrue,final RadioButton rbt1, final RadioButton rbt2,final EditText edt1){
        rbtTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbtTrue.getText().toString();
            }
        });
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTrue.setChecked(false);
                rbt2.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbt1.getText().toString();
            }
        });
        rbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbtTrue.setChecked(false);
                limpiarEditText(edt1);
                posicionDetenidos = rbt2.getText().toString();
            }
        });
        edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                rbt2.setChecked(false);
                rbtTrue.setChecked(false);
                posicionDetenidos = edt1.getText().toString();
            }
        });
    }

    private void listenerDenunciaRechazada(final RadioButton rbt1,final EditText edt){
        rbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarEditText(edt);
                denunciaRechazada = rbt1.getText().toString();
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbt1.setChecked(false);
                denunciaRechazada = rbt1.getText().toString();
            }
        });
    }

    private void listenerViolentado(final RadioButton rbt1, final RadioButton rbt2){
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

    private void listenerDenuncia(final RadioButton rbtSi,final EditText edtDonde, final RadioButton rbtNo, final EditText porQueNo, final RadioButton noSabe){

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

    public void logicaResultadoInvestigacion(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2 , final RadioButton rbt3 , final RadioButton rbt4){
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

    public void listenerOficiales(final RadioButton rbtSi,final RadioButton rbtNo){
        rbtSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtNo.setChecked(false);
                trabajanLosOficiales = rbtSi.getText().toString();
            }
        });
        rbtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtSi.setChecked(false);
                trabajanLosOficiales = rbtNo.getText().toString();
            }
        });
    }

    public void limpiarEditText(EditText edtText){
        edtText.setText("");
        //TODO que el editext pierda el focus al limpiarse.
        //edtText.setFocus;
    }

    public boolean isEmptyString(EditText edt){
        return TextUtils.isEmpty(edt.getText().toString());
    }
}
