package org.lapoderosa.app.normal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityReportVisualizacionBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportVisualizacion extends MasterClass {
    private String id, name, provincia, pais, hora, fecha;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private ActivityReportVisualizacionBinding binding;
    private JSONObject jsonObjectInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportVisualizacionBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        progressDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
            provincia = bundle.getString("provincia");
            pais = bundle.getString("pais");
            hora = bundle.getString("hora");
            fecha = bundle.getString("fecha");
        }

        inicializarVariables();

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_CONSEGUIR_REPORTE));

        binding.pdfIcon.setOnClickListener(view -> {
            MyAnimation.blink(view, ReportVisualizacion.this);
        });

        binding.editIcon.setOnClickListener(view -> {
            MyAnimation.blink(view, ReportVisualizacion.this);

            Bundle b = new Bundle();
            b.putSerializable("objects", basicInfo());

            Intent intent = new Intent(this, ReportEdicion.class);
            intent.putExtra("extra", b);
            intent.putExtra("json", jsonObjectInfo.toString());
            startActivity(intent);
        });
    }

    private ArrayList<Object> basicInfo(){
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(name);
        objects.add(provincia);
        objects.add(pais);
        objects.add(hora);
        objects.add(fecha);
        objects.add(id);
        return objects;
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
        binding.vFecha.setText(fecha);
        binding.vHora.setText(hora);
        binding.vName.setText(name);
        binding.vPais.setText(pais);
        binding.vProvincia.setText(provincia);
    }

    @Override
    protected void responseConexion(String response) {
        try {
            jsonObjectInfo = new JSONObject(response);
            //VICTIMA
            binding.ruvNombreVictima.setText(jsonObjectInfo.getString("usu_nombre_victima"));
            binding.ruvApellidoVictima.setText(jsonObjectInfo.getString("usu_apellido_victima"));
            binding.ruvGeneroVictima.setText(jsonObjectInfo.getString("usu_genero_victima"));
            binding.ruvEdadVictima.setText(jsonObjectInfo.getString("usu_edad_victima"));
            binding.ruvDocumentoVictima.setText(jsonObjectInfo.getString("usu_documento_victima"));
            binding.ruvNacionalidadVictima.setText(jsonObjectInfo.getString("usu_nacionalidad_victima"));
            binding.ruvDireccionVictima.setText(jsonObjectInfo.getString("usu_direccion_victima"));
            binding.ruvBarrioVictima.setText(jsonObjectInfo.getString("usu_barrio_victima"));
            binding.ruvTelefonoVictima.setText(jsonObjectInfo.getString("usu_telefono_victima"));

            //TRASLAD
            binding.ruvTraslado.setText(jsonObjectInfo.getString("usu_traslado"));
            binding.ruvComisariaTraslado.setText(jsonObjectInfo.getString("usu_comisaria"));
            binding.ruvEsposadoTraslado.setText(jsonObjectInfo.getString("usu_esposado"));

            //RESULTADO INVESTIGACION
            binding.ruvResultadoInvestigacion.setText(jsonObjectInfo.getString("usu_resultado_investigacion"));
            binding.ruvTrabajanOficiales.setText(jsonObjectInfo.getString("usu_trabajan_los_oficiales"));

            //OMISION ACTUAR
            binding.ruvMediosDeAsistencia.setText(jsonObjectInfo.getString("usu_medios_de_asistencia"));
            binding.ruvQuienAsistencia.setText(jsonObjectInfo.getString("usu_a_quien_asistencia"));
            binding.ruvDenunciaRechazada.setText(jsonObjectInfo.getString("usu_denuncia_rechazada"));
            binding.ruvViolentado.setText(jsonObjectInfo.getString("usu_violentado"));
            binding.ruvDenunciaFinal.setText(jsonObjectInfo.getString("usu_denuncia_final"));

            //MODALIDAD DETENCION
            binding.ruvPosicionDetenido.setText(jsonObjectInfo.getString("usu_posicion_detenido"));
            binding.ruvTiempoDetenido.setText(jsonObjectInfo.getString("usu_tiempo_detenido"));

            //HECHO POLICIAL
            binding.ruvDiaHecho.setText(jsonObjectInfo.getString("usu_dia_hecho"));
            binding.ruvFechaHecho.setText(jsonObjectInfo.getString("usu_hora_hecho"));
            binding.ruvCuantosAcompanianHecho.setText(jsonObjectInfo.getString("usu_cuantos_acompanian"));
            binding.ruvCualLugarHecho.setText(jsonObjectInfo.getString("usu_cual_lugar"));
            binding.ruvProvinciaHecho.setText(jsonObjectInfo.getString("usu_provincia_hecho"));
            binding.ruvPaisHecho.setText(jsonObjectInfo.getString("usu_pais_hecho"));
            binding.ruvDireccionHecho.setText(jsonObjectInfo.getString("usu_direccion_hecho"));
            binding.ruvBarrioHecho.setText(jsonObjectInfo.getString("usu_barrio_hecho"));

            //FUERZAS INTERVENIENTES
            binding.ruvFuerzasIntervinientes.setText(jsonObjectInfo.getString("usu_fuerzas_intervinientes"));
            binding.ruvCentidadAgentes.setText(jsonObjectInfo.getString("usu_cantidad_agentes"));
            binding.ruvNombreAgente.setText(jsonObjectInfo.getString("usu_nombres_agentes"));
            binding.ruvApodos.setText(jsonObjectInfo.getString("usu_apodos"));
            binding.ruvCantidadVehiculos.setText(jsonObjectInfo.getString("usu_cantidad_vehiculos"));
            binding.ruvNumeroMovil.setText(jsonObjectInfo.getString("usu_num_movil"));
            binding.ruvDominioFuerzas.setText(jsonObjectInfo.getString("usu_dominio"));
            binding.ruvConductaFuerzas.setText(jsonObjectInfo.getString("usu_conducta_agentes"));

            //CARACTERISTICAS PROCEDIMIENTO
            binding.ruvMotivoProcedimiento.setText(jsonObjectInfo.getString("usu_motivo_procedimiento"));
            binding.ruvMaltratosProcedimientos.setText(jsonObjectInfo.getString("usu_maltratos"));
            binding.ruvLesionesProcedimiento.setText(jsonObjectInfo.getString("usu_lesiones"));

            //ALLANAMIENTO
            binding.ruvOrdenAllanamiento.setText(jsonObjectInfo.getString("usu_orden_allanamiento"));
            binding.ruvAgresionAllanamiento.setText(jsonObjectInfo.getString("usu_agresion_allanamiento"));
            binding.ruvPertenenciasAllanamiento.setText(jsonObjectInfo.getString("usu_pertenencias_allanamiento"));
            binding.ruvOmisionPertenencias.setText(jsonObjectInfo.getString("usu_omision_pertenencias"));
            binding.ruvDetenidosAllanamiento.setText(jsonObjectInfo.getString("usu_detenidos_allanamiento"));
            binding.ruvDuracionAllanamiento.setText(jsonObjectInfo.getString("usu_duracion_allanamiento"));
            binding.ruvEsposadosAllanamiento.setText(jsonObjectInfo.getString("usu_esposados"));
            binding.ruvPosicionAllanamiento.setText(jsonObjectInfo.getString("usu_posicion_allanamiento"));

            //ENTREVISTADO
            binding.ruvParentesco.setText(jsonObjectInfo.getString("usu_parentesco_entrevistado"));

            //ENTREVISTADOR
            binding.ruvNombreEntrevistador.setText(jsonObjectInfo.getString("usu_nombre"));
            binding.ruvApellidoEntrevistador.setText(jsonObjectInfo.getString("usu_apellido"));
            binding.ruvAsambleaEntrevistador.setText(jsonObjectInfo.getString("usu_asamblea"));
            binding.ruvFechaEntrevistador.setText(jsonObjectInfo.getString("usu_fecha"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
