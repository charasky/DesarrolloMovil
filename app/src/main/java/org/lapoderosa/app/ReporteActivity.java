package org.lapoderosa.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lapoderosa.app.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReporteActivity extends MasterClass{
    private static final String TAG = "ReporteActivity";
    private TextView tvDateEntrevista,tvDateHecho,tvHoraHecho;
    private DatePickerDialog.OnDateSetListener m1DateSetListener;
    private DatePickerDialog.OnDateSetListener m2DateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button guardar;
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
        listenerMotivoProcedimiento(rbtAveriguacion,rbtDelito,rbtRequiza,rbtNoMotivos);
        listenerMotivoProcedimiento(rbtRequiza,rbtDelito,rbtAveriguacion,rbtNoMotivos);
        listenerMotivoProcedimiento(rbtNoMotivos,rbtDelito,rbtAveriguacion,rbtRequiza);

        //logica maltratos

        listenerMaltratos(rbtNoMaltratos,rbtVerbales,rbtFisicos,rbtAmenazas,rbtDañoPertenencias,rbtRequizaHumillante);
        listenerMaltratos(rbtVerbales,rbtNoMaltratos,rbtFisicos,rbtAmenazas,rbtDañoPertenencias,rbtRequizaHumillante);
        listenerMaltratos(rbtFisicos,rbtVerbales,rbtNoMaltratos,rbtAmenazas,rbtDañoPertenencias,rbtRequizaHumillante);
        listenerMaltratos(rbtAmenazas,rbtFisicos,rbtVerbales,rbtNoMaltratos,rbtDañoPertenencias,rbtRequizaHumillante);
        listenerMaltratos(rbtDañoPertenencias,rbtAmenazas,rbtFisicos,rbtVerbales,rbtNoMaltratos,rbtRequizaHumillante);
        listenerMaltratos(rbtRequizaHumillante,rbtDañoPertenencias,rbtAmenazas,rbtFisicos,rbtVerbales,rbtNoMaltratos);

        //logica lesiones

        if(rbtNoLesiones.isChecked()){

            limpiarEditText(edtFisica);
            limpiarEditText(edtPsiquica);
            limpiarEditText(edtInsomnioPanico);
            limpiarEditText(edtOtrosMiedos);
            lesiones = rbtNoLesiones.getText().toString();
        }

        if(!emptyString(edtFisica)){
            rbtNoLesiones.setChecked(false);
            limpiarEditText(edtPsiquica);
            limpiarEditText(edtInsomnioPanico);
            limpiarEditText(edtOtrosMiedos);
            lesiones = edtFisica.getText().toString();
        }

        if(!emptyString(edtPsiquica)){
            rbtNoLesiones.setChecked(false);
            limpiarEditText(edtFisica);
            limpiarEditText(edtInsomnioPanico);
            limpiarEditText(edtOtrosMiedos);
            lesiones = edtPsiquica.getText().toString();
        }
        if(!emptyString(edtInsomnioPanico)){
            rbtNoLesiones.setChecked(false);
            limpiarEditText(edtFisica);
            limpiarEditText(edtPsiquica);
            limpiarEditText(edtOtrosMiedos);
            lesiones = edtInsomnioPanico.getText().toString();
        }
        if(!emptyString(edtOtrosMiedos)){
            rbtNoLesiones.setChecked(false);
            limpiarEditText(edtFisica);
            limpiarEditText(edtInsomnioPanico);
            limpiarEditText(edtPsiquica);
            lesiones = edtOtrosMiedos.getText().toString();
        }

        //TRASLADO
        /*
        if(rbtNoTrasladaron.isChecked()){
            limpiarEditText(edtDondeTrasladaron);
            traslado = rbtNoTrasladaron.getText().toString();
        }else if(!emptyString(edtDondeTrasladaron)){
            rbtNoTrasladaron.setChecked(false);
            traslado = edtDondeTrasladaron.getText().toString();

        }*/
        rbtYEdt(edtDondeTrasladaron,rbtNoTrasladaron,traslado);

        //COMISARIA/DEPENDENCIA

        rbtYEdt(edtCualComisaria,rbtNoComisaria,comisaria);

        //ESPOSADOS

        if(rbtNoEsposados.isChecked()){
            rbtSiEsposados.setChecked(false);
            esposado = rbtNoEsposados.getText().toString();
        }
        if(rbtSiEsposados.isChecked()){
            rbtNoEsposados.setChecked(false);
            esposado = rbtSiEsposados.getText().toString();
        }

        //ALLANAMIENTO

        if (rbtNoOrdenAllanamiento.isChecked()){
            rbtSiOrdenAllanamiento.setChecked(false);
            ordenAllanamiento = rbtNoOrdenAllanamiento.getText().toString();
        }
        if(rbtSiOrdenAllanamiento.isChecked()){
            rbtNoOrdenAllanamiento.setChecked(false);
            ordenAllanamiento = rbtSiOrdenAllanamiento.getText().toString();
        }

        //AGRESIONES ALLANAMIENTO

        rbtYEdt(edtCualesAgresionesDomicilio,rbtNoAgresionesDomicilio,agresionAllanamiento);

        //pertenencias Domicilio
        rbtYEdt(edtCualesPertenencias,rbtNoPertenenciasRobadas,pertenenciasAllanamiento);

        //omision de pertenencias

        rbtYEdt(edtCualesOmitieron,rbtNoOmitieronPertenencias,omisionPertenencias);

        //detenidos allanamiento

        rbtYEdt(edtCuantosDetenidos,rbtNoDetenidos,detenidosAllanamiento);

        //modalidad detencion allanamiento


        if(rbtParado.isChecked()){
            rbtArrodillado.setChecked(false);
            rbtAcostado.setChecked(false);
            limpiarEditText(edtOtraPosicion);
        }

        if(rbtArrodillado.isChecked()){
            rbtParado.setChecked(false);
            rbtAcostado.setChecked(false);
            limpiarEditText(edtOtraPosicion);
        }

        if(rbtAcostado.isChecked()){
            rbtArrodillado.setChecked(false);
            rbtParado.setChecked(false);
            limpiarEditText(edtOtraPosicion);
        }
        if(!emptyString(edtOtraPosicion)){
            rbtArrodillado.setChecked(false);
            rbtParado.setChecked(false);
            rbtAcostado.setChecked(false);

        }

        //ESPOSADOS ALLANAMIENTO

        if(rbtNoEsposadosAllanamiento.isChecked()){
            rbtSiEsposadosAllanamiento.setChecked(false);
            esposados = rbtNoEsposadosAllanamiento.getText().toString();
        }
        if (rbtSiEsposadosAllanamiento.isChecked()){
            rbtNoEsposadosAllanamiento.setChecked(false);
            esposados = rbtSiEsposadosAllanamiento.getText().toString();
        }

        //logica concurrio a denuncia y no la tomaron

        if(rbtNoDenuncia.isChecked()){
            limpiarEditText(edtMotivosDenunciaRechazada);
            rbtNoViolentado.setChecked(false);
            rbtSiViolentado.setChecked(false);
            denunciaRechazada = rbtNoDenuncia.getText().toString();
        }
        if(!emptyString(edtMotivosDenunciaRechazada)){
            rbtNoDenuncia.setChecked(false);
            denunciaRechazada = edtMotivosDenunciaRechazada.getText().toString();
        }

        if(emptyString(edtMotivosDenunciaRechazada)){
            rbtNoViolentado.setChecked(false);
            rbtSiViolentado.setChecked(false);
        }

        if(rbtNoViolentado.isChecked()){
            rbtSiViolentado.setChecked(false);
            violentado = rbtNoViolentado.getText().toString();
        }
        if(rbtSiViolentado.isChecked()){
            rbtNoViolentado.setChecked(false);
            violentado = rbtSiViolentado.getText().toString();
        }

        //logica denuncia

        if(rbtSiHizoDenuncia.isChecked()){
            rbtNoHizoDenuncia.setChecked(false);
            hizoDenuncia = rbtSiHizoDenuncia.getText().toString();
            dondeDenuncia = edtDondeDenuncia.getText().toString();
        }
        if(rbtNoHizoDenuncia.isChecked()){
            rbtSiHizoDenuncia.setChecked(false);
            hizoDenuncia = rbtNoHizoDenuncia.getText().toString();
            porQueNoDenuncia = edtPorQueNoDenuncia.getText().toString();
        }
        if(rbtNoSabeHacerDenuncia.isChecked()){
            rbtSiHizoDenuncia.setChecked(false);
            rbtNoHizoDenuncia.setChecked(false);
            limpiarEditText(edtDondeDenuncia);
            limpiarEditText(edtPorQueNoDenuncia);
            denunciaFinal = rbtNoSabeHacerDenuncia.getText().toString();
        }

        if(!emptyString(edtDondeDenuncia) ){
            denunciaFinal = dondeDenuncia;
        }


        if(!emptyString(edtPorQueNoDenuncia)){
            denunciaFinal = porQueNoDenuncia;
        }

        //RESULTADO INVESTIGACION

        logicaResultadoInvestigacion(rbtCondena,rbtAbsolucion,rbtCausaEnTramite,rbtArchivo,rbtNoSabeResultado);
        logicaResultadoInvestigacion(rbtAbsolucion,rbtCondena,rbtCausaEnTramite,rbtArchivo,rbtNoSabeResultado);
        logicaResultadoInvestigacion(rbtCausaEnTramite,rbtCondena,rbtAbsolucion,rbtArchivo,rbtNoSabeResultado);
        logicaResultadoInvestigacion(rbtArchivo,rbtCondena,rbtAbsolucion,rbtCausaEnTramite,rbtNoSabeResultado);
        logicaResultadoInvestigacion(rbtNoSabeResultado,rbtCondena,rbtAbsolucion,rbtCausaEnTramite,rbtArchivo);

        //TRABAJAN LOS OFICIALES

        if(rbtNoTrabajaMas.isChecked()){
            rbtSiTrabaja.setChecked(false);
            trabajanLosOficiales = rbtNoTrabajaMas.getText().toString();
        }
        if(rbtSiTrabaja.isChecked()){
            rbtNoTrabajaMas.setChecked(false);
            trabajanLosOficiales = rbtSiTrabaja.getText().toString();
        }


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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarServicio("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/insertar_datos_entrevistador.php");

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
        parametros.put("usu_maltratos",maltratos);
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
        if(!TextUtils.isEmpty(otraAgresion)){
            maltratos = otraAgresion;
        }
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
            Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(intent);
            finish();
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

    }

    public void listenerMaltratos(final RadioButton rbtTrue, final RadioButton rbt1, final RadioButton rbt2,final RadioButton rbt3, final RadioButton rbt4, final RadioButton rbt5){
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
    }

    public void limpiarEditText(EditText edtText){
        edtText.setText("");
    }

    public boolean emptyString(EditText edt){
        return TextUtils.isEmpty(edt.getText().toString());
    }

     private void rbtYEdt(EditText edt,RadioButton rbt,String variable) {
         if (rbt.isChecked()) {
             limpiarEditText(edt);
             variable = rbt.getText().toString();
         } else if (!emptyString(edt)) {
             rbt.setChecked(false);
             variable = edt.getText().toString();

         }
     }
}
