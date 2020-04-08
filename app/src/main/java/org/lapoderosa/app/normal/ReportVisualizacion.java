package org.lapoderosa.app.normal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportVisualizacion extends MasterClass{
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
    private ImageView pdfIcon, editIcon;
    private JSONObject jsonObjectInfo;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualizacion);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        progressDialog = new ProgressDialog(this);
        pdfIcon = findViewById(R.id.pdf_icon);
        editIcon = findViewById(R.id.edit_icon);

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

        pdfIcon.setOnClickListener(view -> {
            MyAnimation.blink(view,ReportVisualizacion.this);
        });

        editIcon.setOnClickListener(view -> {
            MyAnimation.blink(view,ReportVisualizacion.this);
            startActivity(new Intent(ReportVisualizacion.this, ReportEdicion.class));
        });
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        }
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
    protected void inicializarVariables() {
    }

    @Override
    protected void responseConexion(String response) {
        try {
            jsonObjectInfo = new JSONObject(response);
            //VICTIMA
            ruvNombreVictima.setText(jsonObjectInfo.getString("usu_nombre_victima"));
            ruvApellidoVictima.setText(jsonObjectInfo.getString("usu_apellido_victima"));
            ruvGeneroVictima.setText(jsonObjectInfo.getString("usu_genero_victima"));
            ruvEdadVictima.setText(jsonObjectInfo.getString("usu_edad_victima"));
            ruvDocumentoVictima.setText(jsonObjectInfo.getString("usu_documento_victima"));
            ruvNacionalidadVictima.setText(jsonObjectInfo.getString("usu_nacionalidad_victima"));
            ruvDireccionVictima.setText(jsonObjectInfo.getString("usu_direccion_victima"));
            ruvBarrioVictima.setText(jsonObjectInfo.getString("usu_barrio_victima"));
            ruvTelefonoVictima.setText(jsonObjectInfo.getString("usu_telefono_victima"));

            //TRASLAD
            ruvTraslado.setText(jsonObjectInfo.getString("usu_traslado"));
            ruvComisariaTraslado.setText(jsonObjectInfo.getString("usu_comisaria"));
            ruvEsposadoTraslado.setText(jsonObjectInfo.getString("usu_esposado"));

            //RESULTADO INVESTIGACION
            ruvResultadoInvestigacion.setText(jsonObjectInfo.getString("usu_resultado_investigacion"));
            ruvTrabajanOficiales.setText(jsonObjectInfo.getString("usu_trabajan_los_oficiales"));

            //OMISION ACTUAR
            ruvMediosDeAsistencia.setText(jsonObjectInfo.getString("usu_medios_de_asistencia"));
            ruvQuienAsistencia.setText(jsonObjectInfo.getString("usu_a_quien_asistencia"));
            ruvDenunciaRechazada.setText(jsonObjectInfo.getString("usu_denuncia_rechazada"));
            ruvViolentado.setText(jsonObjectInfo.getString("usu_violentado"));
            ruvDenunciaFinal.setText(jsonObjectInfo.getString("usu_denuncia_final"));

            //MODALIDAD DETENCION
            ruvPosicionDetenido.setText(jsonObjectInfo.getString("usu_posicion_detenido"));
            ruvTiempoDetenido.setText(jsonObjectInfo.getString("usu_tiempo_detenido"));

            //HECHO POLICIAL
            ruvDiaHecho.setText(jsonObjectInfo.getString("usu_dia_hecho"));
            ruvFechaHecho.setText(jsonObjectInfo.getString("usu_hora_hecho"));
            ruvCuantosAcompañanHecho.setText(jsonObjectInfo.getString("usu_cuantos_acompanian"));
            ruvCualLugarHecho.setText(jsonObjectInfo.getString("usu_cual_lugar"));
            ruvProvinciaHecho.setText(jsonObjectInfo.getString("usu_provincia_hecho"));
            ruvPaisHecho.setText(jsonObjectInfo.getString("usu_pais_hecho"));
            ruvDireccionHecho.setText(jsonObjectInfo.getString("usu_direccion_hecho"));
            ruvBarrioHecho.setText(jsonObjectInfo.getString("usu_barrio_hecho"));

            //FUERZAS INTERVENIENTES
            ruvFuerzasIntervinientes.setText(jsonObjectInfo.getString("usu_fuerzas_intervinientes"));
            ruvCentidadAgentes.setText(jsonObjectInfo.getString("usu_cantidad_agentes"));
            ruvNombreAgente.setText(jsonObjectInfo.getString("usu_nombres_agentes"));
            ruvApodos.setText(jsonObjectInfo.getString("usu_apodos"));
            ruvCantidadVehiculos.setText(jsonObjectInfo.getString("usu_cantidad_vehiculos"));
            ruvNumeroMovil.setText(jsonObjectInfo.getString("usu_num_movil"));
            ruvDominioFuerzas.setText(jsonObjectInfo.getString("usu_dominio"));
            ruvConductaFuerzas.setText(jsonObjectInfo.getString("usu_conducta_agentes"));

            //CARACTERISTICAS PROCEDIMIENTO
            ruvMotivoProcedimiento.setText(jsonObjectInfo.getString("usu_motivo_procedimiento"));
            ruvMaltratosProcedimientos.setText(jsonObjectInfo.getString("usu_maltratos"));
            ruvLesionesProcedimiento.setText(jsonObjectInfo.getString("usu_lesiones"));

            //ALLANAMIENTO
            ruvOrdenAllanamiento.setText(jsonObjectInfo.getString("usu_orden_allanamiento"));
            ruvAgresionAllanamiento.setText(jsonObjectInfo.getString("usu_agresion_allanamiento"));
            ruvPertenenciasAllanamiento.setText(jsonObjectInfo.getString("usu_pertenencias_allanamiento"));
            ruvOmisionPertenencias.setText(jsonObjectInfo.getString("usu_omision_pertenencias"));
            ruvDetenidosAllanamiento.setText(jsonObjectInfo.getString("usu_detenidos_allanamiento"));
            ruvDuracionAllanamiento.setText(jsonObjectInfo.getString("usu_duracion_allanamiento"));
            ruvEsposadosAllanamiento.setText(jsonObjectInfo.getString("usu_esposados"));
            ruvPosicionAllanamiento.setText(jsonObjectInfo.getString("usu_posicion_allanamiento"));

            //ENTREVISTADO
            ruvParentesco.setText(jsonObjectInfo.getString("usu_parentesco_entrevistado"));

            //ENTREVISTADOR
            ruvNombreEntrevistador.setText(jsonObjectInfo.getString("usu_nombre"));
            ruvApellidoEntrevistador.setText(jsonObjectInfo.getString("usu_apellido"));
            ruvAsambleaEntrevistador.setText(jsonObjectInfo.getString("usu_asamblea"));
            ruvFechaEntrevistador.setText(jsonObjectInfo.getString("usu_fecha"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
