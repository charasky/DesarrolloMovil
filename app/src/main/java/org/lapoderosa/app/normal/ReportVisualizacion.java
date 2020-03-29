package org.lapoderosa.app.normal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.SharedPrefManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportVisualizacion extends MasterClass {
    private TextView fullNameVictima, provincia, pais, hora, fecha;
    private String id;

    //VICTIMA
    private TextView ruvNombreVictima, ruvApellidoVictima, ruvGeneroVictima, ruvEdadVictima, ruvDocumentoVictima, ruvNacionalidadVictima, ruvDireccionVictima, ruvBarrioVictima, ruvTelefonoVictima;

    //TRASLADO
    private TextView ruvTraslado, ruvEsposadoTraslado, ruvComisariaTraslado;

    //RESULTADO INVESTIGACION
    private TextView ruvResultadoInvestigacion, ruvTrabajanOficiales;

    //OMISION ACTUAR
    private TextView ruvMediosDeAsistencia, ruvQuienAsistencia, ruvDenunciaRechazada, ruvViolentado, ruvDenunciaFinal;

    //MODALIDAD DETENCION
    private TextView ruvPosicionDetenido, ruvTiempoDetenido;

    //HECHO POLICIAL
    private TextView ruvDiaHecho, ruvFechaHecho, ruvCuantosAcompañanHecho, ruvCualLugarHecho, ruvProvinciaHecho, ruvPaisHecho, ruvDireccionHecho, ruvBarrioHecho;

    //FUERZAS INTERVENIENTES
    private TextView ruvFuerzasIntervinientes, ruvCentidadAgentes, ruvNombreAgente, ruvApodos, ruvCantidadVehiculos, ruvNumeroMovil, ruvDominioFuerzas, ruvConductaFuerzas;

    //CARACTERISTICAS PROCEDIMIENTO
    private TextView ruvMotivoProcedimiento, ruvMaltratosProcedimientos, ruvLesionesProcedimiento;

    //ALLANAMIENTO
    private TextView ruvOrdenAllanamiento, ruvAgresionAllanamiento, ruvPertenenciasAllanamiento, ruvOmisionPertenencias, ruvDetenidosAllanamiento, ruvDuracionAllanamiento, ruvEsposadosAllanamiento, ruvPosicionAllanamiento;

    //ENTREVISTADO
    private TextView ruvParentesco;

    //ENTREVISTADOR
    private TextView ruvNombreEntrevistador, ruvApellidoEntrevistador, ruvAsambleaEntrevistador, ruvFechaEntrevistador;
    private Button btPdf;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualizacion);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        progressDialog = new ProgressDialog(this);

        btPdf = findViewById(R.id.btPdf);

        fullNameVictima = findViewById(R.id.vName);
        provincia = findViewById(R.id.vProvincia);
        pais = findViewById(R.id.vPais);
        hora = findViewById(R.id.vHora);
        fecha = findViewById(R.id.vFecha);

        //VICTIMA
        ruvNombreVictima = findViewById(R.id.ruvNombreVictima);
        ruvApellidoVictima = findViewById(R.id.ruvApellidoVictima);
        ruvGeneroVictima = findViewById(R.id.ruvGeneroVictima);
        ruvEdadVictima = findViewById(R.id.ruvEdadVictima);
        ruvDocumentoVictima = findViewById(R.id.ruvDocumentoVictima);
        ruvNacionalidadVictima = findViewById(R.id.ruvNacionalidadVictima);
        ruvDireccionVictima = findViewById(R.id.ruvDireccionVictima);
        ruvBarrioVictima = findViewById(R.id.ruvBarrioVictima);
        ruvTelefonoVictima = findViewById(R.id.ruvTelefonoVictima);

        //TRASLADO
        ruvTraslado = findViewById(R.id.ruvTraslado);
        ruvEsposadoTraslado = findViewById(R.id.ruvEsposadoTraslado);
        ruvComisariaTraslado = findViewById(R.id.ruvComisariaTraslado);

        //RESULTADO INVESTIGACION
        ruvResultadoInvestigacion = findViewById(R.id.ruvResultadoInvestigacion);
        ruvTrabajanOficiales = findViewById(R.id.ruvTrabajanOficiales);

        //OMISION ACTUAR
        ruvMediosDeAsistencia = findViewById(R.id.ruvMediosDeAsistencia);
        ruvQuienAsistencia = findViewById(R.id.ruvQuienAsistencia);
        ruvDenunciaRechazada = findViewById(R.id.ruvDenunciaRechazada);
        ruvViolentado = findViewById(R.id.ruvViolentado);
        ruvDenunciaFinal = findViewById(R.id.ruvDenunciaFinal);

        //MODALIDAD DETENCION
        ruvPosicionDetenido = findViewById(R.id.ruvPosicionDetenido);
        ruvTiempoDetenido = findViewById(R.id.ruvTiempoDetenido);

        //HECHO POLICIAL
        ruvDiaHecho = findViewById(R.id.ruvDiaHecho);
        ruvFechaHecho = findViewById(R.id.ruvFechaHecho);
        ruvCuantosAcompañanHecho = findViewById(R.id.ruvCuantosAcompañanHecho);
        ruvCualLugarHecho = findViewById(R.id.ruvCualLugarHecho);
        ruvProvinciaHecho = findViewById(R.id.ruvProvinciaHecho);
        ruvPaisHecho = findViewById(R.id.ruvPaisHecho);
        ruvDireccionHecho = findViewById(R.id.ruvDireccionHecho);
        ruvBarrioHecho = findViewById(R.id.ruvBarrioHecho);

        //FUERZAS INTERVENIENTES
        ruvFuerzasIntervinientes = findViewById(R.id.ruvFuerzasIntervinientes);
        ruvCentidadAgentes = findViewById(R.id.ruvCentidadAgentes);
        ruvNombreAgente = findViewById(R.id.ruvNombreAgente);
        ruvApodos = findViewById(R.id.ruvApodos);
        ruvCantidadVehiculos = findViewById(R.id.ruvCantidadVehiculos);
        ruvNumeroMovil = findViewById(R.id.ruvNumeroMovil);
        ruvDominioFuerzas = findViewById(R.id.ruvDominioFuerzas);
        ruvConductaFuerzas = findViewById(R.id.ruvConductaFuerzas);

        //CARACTERISTICAS PROCEDIMIENTO
        ruvMotivoProcedimiento = findViewById(R.id.ruvMotivoProcedimiento);
        ruvMaltratosProcedimientos = findViewById(R.id.ruvMaltratosProcedimientos);
        ruvLesionesProcedimiento = findViewById(R.id.ruvLesionesProcedimiento);

        //ALLANAMIENTO
        ruvOrdenAllanamiento = findViewById(R.id.ruvOrdenAllanamiento);
        ruvAgresionAllanamiento = findViewById(R.id.ruvAgresionAllanamiento);
        ruvPertenenciasAllanamiento = findViewById(R.id.ruvPertenenciasAllanamiento);
        ruvOmisionPertenencias = findViewById(R.id.ruvOmisionPertenencias);
        ruvDetenidosAllanamiento = findViewById(R.id.ruvDetenidosAllanamiento);
        ruvDuracionAllanamiento = findViewById(R.id.ruvDuracionAllanamiento);
        ruvEsposadosAllanamiento = findViewById(R.id.ruvEsposadosAllanamiento);
        ruvPosicionAllanamiento = findViewById(R.id.ruvPosicionAllanamiento);

        //ENTREVISTADO
        ruvParentesco = findViewById(R.id.ruvParentesco);

        //ENTREVISTADOR
        ruvNombreEntrevistador = findViewById(R.id.ruvNombreEntrevistador);
        ruvApellidoEntrevistador = findViewById(R.id.ruvApellidoEntrevistador);
        ruvAsambleaEntrevistador = findViewById(R.id.ruvAsambleaEntrevistador);
        ruvFechaEntrevistador = findViewById(R.id.ruvFechaEntrevistador);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            fullNameVictima.setText(bundle.getString("name"));
            provincia.setText(bundle.getString("provincia"));
            pais.setText(bundle.getString("pais"));
            hora.setText(bundle.getString("hora"));
            fecha.setText(bundle.getString("fecha"));
        }

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_CONSEGUIR_REPORTE));

        btPdf.setOnClickListener(view -> createPDF());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_usuario", SharedPrefManager.getInstance(this).getKeyUsuario());
        parametros.put("id", id);
        return parametros;
    }

    @Override
    protected void inicializarStringVariables() {
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            //VICTIMA
            ruvNombreVictima.setText(jsonObject.getString("usu_nombre_victima"));
            ruvApellidoVictima.setText(jsonObject.getString("usu_apellido_victima"));
            ruvGeneroVictima.setText(jsonObject.getString("usu_genero_victima"));
            ruvEdadVictima.setText(jsonObject.getString("usu_edad_victima"));
            ruvDocumentoVictima.setText(jsonObject.getString("usu_documento_victima"));
            ruvNacionalidadVictima.setText(jsonObject.getString("usu_nacionalidad_victima"));
            ruvDireccionVictima.setText(jsonObject.getString("usu_direccion_victima"));
            ruvBarrioVictima.setText(jsonObject.getString("usu_barrio_victima"));
            ruvTelefonoVictima.setText(jsonObject.getString("usu_telefono_victima"));

            //TRASLAD
            ruvTraslado.setText(jsonObject.getString("usu_traslado"));
            ruvComisariaTraslado.setText(jsonObject.getString("usu_comisaria"));
            ruvEsposadoTraslado.setText(jsonObject.getString("usu_esposado"));

            //RESULTADO INVESTIGACION
            ruvResultadoInvestigacion.setText(jsonObject.getString("usu_resultado_investigacion"));
            ruvTrabajanOficiales.setText(jsonObject.getString("usu_trabajan_los_oficiales"));

            //OMISION ACTUAR
            ruvMediosDeAsistencia.setText(jsonObject.getString("usu_medios_de_asistencia"));
            ruvQuienAsistencia.setText(jsonObject.getString("usu_a_quien_asistencia"));
            ruvDenunciaRechazada.setText(jsonObject.getString("usu_denuncia_rechazada"));
            ruvViolentado.setText(jsonObject.getString("usu_violentado"));
            ruvDenunciaFinal.setText(jsonObject.getString("usu_denuncia_final"));

            //MODALIDAD DETENCION
            ruvPosicionDetenido.setText(jsonObject.getString("usu_posicion_detenido"));
            ruvTiempoDetenido.setText(jsonObject.getString("usu_tiempo_detenido"));

            //HECHO POLICIAL
            ruvDiaHecho.setText(jsonObject.getString("usu_dia_hecho"));
            ruvFechaHecho.setText(jsonObject.getString("usu_fecha_hecho"));
            ruvCuantosAcompañanHecho.setText(jsonObject.getString("usu_cuantos_acompañan"));
            ruvCualLugarHecho.setText(jsonObject.getString("usu_cual_lugar"));
            ruvProvinciaHecho.setText(jsonObject.getString("usu_provincia_hecho"));
            ruvPaisHecho.setText(jsonObject.getString("usu_pais_hecho"));
            ruvDireccionHecho.setText(jsonObject.getString("usu_direccion_hecho"));
            ruvBarrioHecho.setText(jsonObject.getString("usu_barrio_hecho"));

            //FUERZAS INTERVENIENTES
            ruvFuerzasIntervinientes.setText(jsonObject.getString("usu_fuerzas_intervinientes"));
            ruvCentidadAgentes.setText(jsonObject.getString("usu_cantidad_agentes"));
            ruvNombreAgente.setText(jsonObject.getString("usu_nombres_agentes"));
            ruvApodos.setText(jsonObject.getString("usu_apodos"));
            ruvCantidadVehiculos.setText(jsonObject.getString("usu_cantidad_vehiculos"));
            ruvNumeroMovil.setText(jsonObject.getString("usu_num_movil"));
            ruvDominioFuerzas.setText(jsonObject.getString("usu_dominio"));
            ruvConductaFuerzas.setText(jsonObject.getString("usu_conducta_agentes"));

            //CARACTERISTICAS PROCEDIMIENTO
            ruvMotivoProcedimiento.setText(jsonObject.getString("usu_motivo_procedimiento"));
            ruvMaltratosProcedimientos.setText(jsonObject.getString("usu_maltratos"));
            ruvLesionesProcedimiento.setText(jsonObject.getString("usu_lesiones"));

            //ALLANAMIENTO
            ruvOrdenAllanamiento.setText(jsonObject.getString("usu_orden_allanamiento"));
            ruvAgresionAllanamiento.setText(jsonObject.getString("usu_agresion_allanamiento"));
            ruvPertenenciasAllanamiento.setText(jsonObject.getString("usu_pertenencias_allanamiento"));
            ruvOmisionPertenencias.setText(jsonObject.getString("usu_omision_pertenencias"));
            ruvDetenidosAllanamiento.setText(jsonObject.getString("usu_detenidos_allanamiento"));
            ruvDuracionAllanamiento.setText(jsonObject.getString("usu_duracion_allanamiento"));
            ruvEsposadosAllanamiento.setText(jsonObject.getString("usu_esposados"));
            ruvPosicionAllanamiento.setText(jsonObject.getString("usu_posicion_allanamiento"));

            //ENTREVISTADO
            ruvParentesco.setText(jsonObject.getString("usu_parentesco_entrevistado"));

            //ENTREVISTADOR
            ruvNombreEntrevistador.setText(jsonObject.getString("usu_nombre"));
            ruvApellidoEntrevistador.setText(jsonObject.getString("usu_apellido"));
            ruvAsambleaEntrevistador.setText(jsonObject.getString("usu_asamblea"));
            ruvFechaEntrevistador.setText(jsonObject.getString("usu_fecha"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDF() {
        String namePDF = fullNameVictima.getText().toString() + "_" + DateDefinido.getFechaDispositivo() + "_-_" + DateDefinido.getHoraDispositivo();
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);

        Canvas canvas = myPage1.getCanvas();

        canvas.drawText(fullNameVictima.getText().toString(), 40, 50, myPaint);
        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/" + namePDF + ".pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
        Toast.makeText(ReportVisualizacion.this, "creado", Toast.LENGTH_LONG).show();
    }
}
