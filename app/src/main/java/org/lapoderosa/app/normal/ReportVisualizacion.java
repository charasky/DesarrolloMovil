package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.adapter.UserAdapter;
import org.lapoderosa.app.admin.AdminHabilitarCuenta;
import org.lapoderosa.app.model.User;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualizacion);
        progressDialog = new ProgressDialog(this);

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
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_usuario", SharedPrefManager.getInstance(this).getKeyUsuario());
        parametros.put("id", id);
    }

    @Override
    protected void inicializarStringVariables() {
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Reporte");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                //VICTIMA
                if (i == 0) {
                    ruvNombreVictima.setText(jsonObject1.getString("usu_nombre_victima"));
                    ruvApellidoVictima.setText(jsonObject1.getString("usu_apellido_victima"));
                    ruvGeneroVictima.setText(jsonObject1.getString("usu_genero_victima"));
                    ruvEdadVictima.setText(jsonObject1.getString("usu_edad_victima"));
                    ruvDocumentoVictima.setText(jsonObject1.getString("usu_documento_victima"));
                    ruvNacionalidadVictima.setText(jsonObject1.getString("usu_nacionalidad_victima"));
                    ruvDireccionVictima.setText(jsonObject1.getString("usu_direccion_victima"));
                    ruvBarrioVictima.setText(jsonObject1.getString("usu_barrio_victima"));
                    ruvTelefonoVictima.setText(jsonObject1.getString("usu_telefono_victima"));
                }

                //TRASLADO
                if (i == 1) {
                    ruvTraslado.setText(jsonObject1.getString("usu_traslado"));
                    ruvComisariaTraslado.setText(jsonObject1.getString("usu_comisaria"));
                    ruvEsposadoTraslado.setText(jsonObject1.getString("usu_esposado"));
                }

                //RESULTADO INVESTIGACION
                if (i == 2) {
                    ruvResultadoInvestigacion.setText(jsonObject1.getString("usu_resultado_investigacion"));
                    ruvTrabajanOficiales.setText(jsonObject1.getString("usu_trabajan_los_oficiales"));
                }

                //OMISION ACTUAR
                if (i == 3) {
                    ruvMediosDeAsistencia.setText(jsonObject1.getString("usu_medios_de_asistencia"));
                    ruvQuienAsistencia.setText(jsonObject1.getString("usu_a_quien_asistencia"));
                    ruvDenunciaRechazada.setText(jsonObject1.getString("usu_denuncia_rechazada"));
                    ruvViolentado.setText(jsonObject1.getString("usu_violentado"));
                    ruvDenunciaFinal.setText(jsonObject1.getString("usu_denuncia_final"));
                }

                //MODALIDAD DETENCION
                if (i == 4) {
                    ruvPosicionDetenido.setText(jsonObject1.getString("usu_posicion_detenido"));
                    ruvTiempoDetenido.setText(jsonObject1.getString("usu_tiempo_detenido"));
                }

                //HECHO POLICIAL
                if (i == 5) {
                    ruvDiaHecho.setText(jsonObject1.getString("usu_dia_hecho"));
                    ruvFechaHecho.setText(jsonObject1.getString("usu_fecha_hecho"));
                    ruvCuantosAcompañanHecho.setText(jsonObject1.getString("usu_cuantos_acompañan"));
                    ruvCualLugarHecho.setText(jsonObject1.getString("usu_cual_lugar"));
                    ruvProvinciaHecho.setText(jsonObject1.getString("usu_provincia_hecho"));
                    ruvPaisHecho.setText(jsonObject1.getString("usu_pais_hecho"));
                    ruvDireccionHecho.setText(jsonObject1.getString("usu_direccion_hecho"));
                    ruvBarrioHecho.setText(jsonObject1.getString("usu_barrio_hecho"));
                }

                //FUERZAS INTERVENIENTES
                if (i == 6) {
                    ruvFuerzasIntervinientes.setText(jsonObject1.getString("usu_fuerzas_intervinientes"));
                    ruvCentidadAgentes.setText(jsonObject1.getString("usu_cantidad_agentes"));
                    ruvNombreAgente.setText(jsonObject1.getString("usu_nombres_agentes"));
                    ruvApodos.setText(jsonObject1.getString("usu_apodos"));
                    ruvCantidadVehiculos.setText(jsonObject1.getString("usu_cantidad_vehiculos"));
                    ruvNumeroMovil.setText(jsonObject1.getString("usu_num_movil"));
                    ruvDominioFuerzas.setText(jsonObject1.getString("usu_dominio"));
                    ruvConductaFuerzas.setText(jsonObject1.getString("usu_conducta_agentes"));
                }

                //CARACTERISTICAS PROCEDIMIENTO
                if (i == 7) {
                    ruvMotivoProcedimiento.setText(jsonObject1.getString("usu_motivo_procedimiento"));
                    ruvMaltratosProcedimientos.setText(jsonObject1.getString("usu_maltratos"));
                    ruvLesionesProcedimiento.setText(jsonObject1.getString("usu_lesiones"));
                }

                //ALLANAMIENTO

                if (i == 8) {
                    ruvOrdenAllanamiento.setText(jsonObject1.getString("usu_orden_allanamiento"));
                    ruvAgresionAllanamiento.setText(jsonObject1.getString("usu_agresion_allanamiento"));
                    ruvPertenenciasAllanamiento.setText(jsonObject1.getString("usu_pertenencias_allanamiento"));
                    ruvOmisionPertenencias.setText(jsonObject1.getString("usu_omision_pertenencias"));
                    ruvDetenidosAllanamiento.setText(jsonObject1.getString("usu_detenidos_allanamiento"));
                    ruvDuracionAllanamiento.setText(jsonObject1.getString("usu_duracion_allanamiento"));
                    ruvEsposadosAllanamiento.setText(jsonObject1.getString("usu_esposados"));
                    ruvPosicionAllanamiento.setText(jsonObject1.getString("usu_posicion_allanamiento"));
                }

                //ENTREVISTADO
                if (i == 9) {
                    ruvParentesco.setText(jsonObject1.getString("usu_parentesco_entrevistado"));
                }

                //ENTREVISTADOR
                if (i == 10) {
                    ruvNombreEntrevistador.setText(jsonObject1.getString("usu_nombre"));
                    ruvApellidoEntrevistador.setText(jsonObject1.getString("usu_apellido"));
                    ruvAsambleaEntrevistador.setText(jsonObject1.getString("usu_asamblea"));
                    ruvFechaEntrevistador.setText(jsonObject1.getString("usu_fecha"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
