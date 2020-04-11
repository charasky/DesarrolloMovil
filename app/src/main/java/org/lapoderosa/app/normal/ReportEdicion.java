package org.lapoderosa.app.normal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lapoderosa.app.databinding.ActivityReportEdicionBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.adapter.InfoAdapter;
import org.lapoderosa.app.model.Informacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReportEdicion extends AppCompatActivity {
    private ActivityReportEdicionBinding binding;
    private InfoAdapter adapter;
    private List<Informacion> datosParentescoList, datosVicList, datosTrasladoList, datosResultadoList, datosOmisionActuarList, datosModalidadDetencionList, datosHechoPolicialList, datosFuerzasIntervinientesList, datosEntrevistadorList, datosCaracteProcedimiento, datosAllanamientoList;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportEdicionBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rvLyManager();

        Bundle extra = getIntent().getBundleExtra("extra");

        if (getIntent().hasExtra("json") && extra != null) {
            ArrayList<Object> objects = (ArrayList<Object>) extra.getSerializable("objects");

            binding.vName.setText(objects.get(0).toString());
            binding.vProvincia.setText(objects.get(1).toString());
            binding.vPais.setText(objects.get(2).toString());
            binding.vHora.setText(objects.get(3).toString());
            binding.vFecha.setText(objects.get(4).toString());
            id = objects.get(5).toString();

            try {
                JSONObject mJsonObject = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("json")));
                algo(mJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        myAdapters();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    protected void algo(JSONObject jsonObjectInfo) {
        try {
            datosVicList.add(new Informacion(id,"Nombre:", jsonObjectInfo.getString("usu_nombre_victima"), "usu_nombre_victima", "victima"));
            datosVicList.add(new Informacion(id,"Apellido:", jsonObjectInfo.getString("usu_apellido_victima"), "usu_apellido_victima", "victima"));
            datosVicList.add(new Informacion(id,"Genero:", jsonObjectInfo.getString("usu_genero_victima"), "usu_genero_victima", "victima"));
            datosVicList.add(new Informacion(id,"Edad:", jsonObjectInfo.getString("usu_edad_victima"), "usu_edad_victima", "victima"));
            datosVicList.add(new Informacion(id,"Nacionalidad:", jsonObjectInfo.getString("usu_nacionalidad_victima"), "usu_nacionalidad_victima", "victima"));
            datosVicList.add(new Informacion(id,"Documento:", jsonObjectInfo.getString("usu_documento_victima"), "usu_documento_victima", "victima"));
            datosVicList.add(new Informacion(id,"Direccion:", jsonObjectInfo.getString("usu_direccion_victima"), "usu_direccion_victima", "victima"));
            datosVicList.add(new Informacion(id,"Barrio:", jsonObjectInfo.getString("usu_barrio_victima"), "usu_barrio_victima", "victima"));
            datosVicList.add(new Informacion(id,"Nombre:", jsonObjectInfo.getString("usu_telefono_victima"), "usu_telefono_victima", "victima"));

            datosTrasladoList.add(new Informacion(id,"Fue Traslado:", jsonObjectInfo.getString("usu_traslado"), "usu_traslado", "traslado"));
            datosTrasladoList.add(new Informacion(id,"Cual Comisaria:", jsonObjectInfo.getString("usu_comisaria"), "usu_comisaria", "traslado"));
            datosTrasladoList.add(new Informacion(id,"Fue Esposado:", jsonObjectInfo.getString("usu_esposado"), "usu_esposado", "traslado"));

            datosResultadoList.add(new Informacion(id,"Resultado:", jsonObjectInfo.getString("usu_resultado_investigacion"), "usu_resultado_investigacion", "resultado_investigacion"));
            datosResultadoList.add(new Informacion(id,"Trabajan los Oficiales:", jsonObjectInfo.getString("usu_trabajan_los_oficiales"), "usu_trabajan_los_oficiales", "resultado_investigacion"));

            datosOmisionActuarList.add(new Informacion(id,"Medio de Asistencia:", jsonObjectInfo.getString("usu_medios_de_asistencia"), "usu_medios_de_asistencia", "omision_actuar"));
            datosOmisionActuarList.add(new Informacion(id,"Quien lo asistio:", jsonObjectInfo.getString("usu_a_quien_asistencia"), "usu_a_quien_asistencia", "omision_actuar"));
            datosOmisionActuarList.add(new Informacion(id,"Denuncia fue rechazada:", jsonObjectInfo.getString("usu_denuncia_rechazada"), "usu_denuncia_rechazada", "omision_actuar"));
            datosOmisionActuarList.add(new Informacion(id,"Fue Violentado:", jsonObjectInfo.getString("usu_violentado"), "usu_violentado", "omision_actuar"));
            datosOmisionActuarList.add(new Informacion(id,"Denuncia final:", jsonObjectInfo.getString("usu_denuncia_final"), "usu_denuncia_final", "omision_actuar"));

            datosModalidadDetencionList.add(new Informacion(id,"Posicion al ser Detenido:", jsonObjectInfo.getString("usu_posicion_detenido"), "usu_posicion_detenido", "modalidad_detencion"));
            datosModalidadDetencionList.add(new Informacion(id,"Tiempo Detenido:", jsonObjectInfo.getString("usu_tiempo_detenido"), "usu_tiempo_detenido", "modalidad_detencion"));

            datosHechoPolicialList.add(new Informacion(id,"Dia:", jsonObjectInfo.getString("usu_dia_hecho"), "usu_dia_hecho", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Hora:", jsonObjectInfo.getString("usu_hora_hecho"), "usu_hora_hecho", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Cuantos Acompañaron:", jsonObjectInfo.getString("usu_cuantos_acompanian"), "usu_cuantos_acompanian", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Lugar:", jsonObjectInfo.getString("usu_cual_lugar"), "usu_cual_lugar", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Provincia:", jsonObjectInfo.getString("usu_provincia_hecho"), "usu_provincia_hecho", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Pais:", jsonObjectInfo.getString("usu_pais_hecho"), "usu_pais_hecho", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Direccion:", jsonObjectInfo.getString("usu_direccion_hecho"), "usu_direccion_hecho", "hecho_policial"));
            datosHechoPolicialList.add(new Informacion(id,"Barrio:", jsonObjectInfo.getString("usu_barrio_hecho"), "usu_barrio_hecho", "hecho_policial"));

            datosFuerzasIntervinientesList.add(new Informacion(id,"Tipo de Policia:", jsonObjectInfo.getString("usu_fuerzas_intervinientes"), "usu_fuerzas_intervinientes", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Cantidad Agentes:", jsonObjectInfo.getString("usu_cantidad_agentes"), "usu_cantidad_agentes", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Nombre Agentes:", jsonObjectInfo.getString("usu_nombres_agentes"), "usu_nombres_agentes", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Apodos:", jsonObjectInfo.getString("usu_apodos"), "usu_apodos", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Cantidad Vehiculos:", jsonObjectInfo.getString("usu_cantidad_vehiculos"), "usu_cantidad_vehiculos", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Patente Vehiculos:", jsonObjectInfo.getString("usu_num_movil"), "usu_num_movil", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Dominio:", jsonObjectInfo.getString("usu_dominio"), "usu_dominio", "fuerzas_intervinientes"));
            datosFuerzasIntervinientesList.add(new Informacion(id,"Conducta Agentes:", jsonObjectInfo.getString("usu_conducta_agentes"), "usu_conducta_agentes", "fuerzas_intervinientes"));

            datosCaracteProcedimiento.add(new Informacion(id,"Motivo de Procedimiento", jsonObjectInfo.getString("usu_motivo_procedimiento"), "usu_motivo_procedimiento", "caracteristicas_procedimiento"));
            datosCaracteProcedimiento.add(new Informacion(id,"Hubo Maltratos:", jsonObjectInfo.getString("usu_maltratos"), "usu_maltratos", "caracteristicas_procedimiento"));
            datosCaracteProcedimiento.add(new Informacion(id,"Hubo Lesiones:", jsonObjectInfo.getString("usu_lesiones"), "usu_lesiones", "caracteristicas_procedimiento"));

            datosAllanamientoList.add(new Informacion(id,"Hubo Orden de Allanamiento;", jsonObjectInfo.getString("usu_orden_allanamiento"), "usu_orden_allanamiento", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Hubo Agresion:", jsonObjectInfo.getString("usu_agresion_allanamiento"), "usu_agresion_allanamiento", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Pertenencias Allanadas:", jsonObjectInfo.getString("usu_pertenencias_allanamiento"), "usu_pertenencias_allanamiento", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Hubo Omision Pertenencias:", jsonObjectInfo.getString("usu_omision_pertenencias"), "usu_omision_pertenencias", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Hubo Detenidos:", jsonObjectInfo.getString("usu_detenidos_allanamiento"), "usu_detenidos_allanamiento", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Duracion:", jsonObjectInfo.getString("usu_duracion_allanamiento"), "usu_duracion_allanamiento", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Fue Esposado:", jsonObjectInfo.getString("usu_esposados"), "usu_esposados", "allanamiento"));
            datosAllanamientoList.add(new Informacion(id,"Posicion:", jsonObjectInfo.getString("usu_posicion_allanamiento"), "usu_posicion_allanamiento", "allanamiento"));

            datosParentescoList.add(new Informacion(id,"Parentesco:", jsonObjectInfo.getString("usu_parentesco_entrevistado"), "usu_parentesco_entrevistado", "entrevistado"));

            datosEntrevistadorList.add(new Informacion(id,"Nombre:", jsonObjectInfo.getString("usu_nombre"), "usu_nombre", "entrevistador"));
            datosEntrevistadorList.add(new Informacion(id,"Apellido:", jsonObjectInfo.getString("usu_apellido"), "usu_apellido", "entrevistador"));
            datosEntrevistadorList.add(new Informacion(id,"Asamblea:", jsonObjectInfo.getString("usu_asamblea"), "usu_asamblea", "entrevistador"));
            datosEntrevistadorList.add(new Informacion(id,"Fecha De Modificacion:", jsonObjectInfo.getString("usu_fecha"), "usu_fecha", "entrevistador"));

            myAdapters();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void rvLyManager() {
        datosVicList = new ArrayList<>();
        datosTrasladoList = new ArrayList<>();
        datosResultadoList = new ArrayList<>();
        datosOmisionActuarList = new ArrayList<>();
        datosModalidadDetencionList = new ArrayList<>();
        datosHechoPolicialList = new ArrayList<>();
        datosFuerzasIntervinientesList = new ArrayList<>();
        datosEntrevistadorList = new ArrayList<>();
        datosCaracteProcedimiento = new ArrayList<>();
        datosAllanamientoList = new ArrayList<>();
        datosParentescoList = new ArrayList<>();

        binding.rvVicInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvTrasladoInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvResulInvesInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvOmisionActuarInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvModalidadDetencionInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvHechoPolicialInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvFuerzasIntervinientesInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvEntrevistadorInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvCaracteProcedimientoInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvAllanamientoInfo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvEntrevistadoInfo.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void myAdapters() {
        adapter = new InfoAdapter(ReportEdicion.this, datosVicList);
        binding.rvVicInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosTrasladoList);
        binding.rvTrasladoInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosResultadoList);
        binding.rvResulInvesInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosOmisionActuarList);
        binding.rvOmisionActuarInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosModalidadDetencionList);
        binding.rvModalidadDetencionInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosHechoPolicialList);
        binding.rvHechoPolicialInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosFuerzasIntervinientesList);
        binding.rvFuerzasIntervinientesInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosEntrevistadorList);
        binding.rvEntrevistadorInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosCaracteProcedimiento);
        binding.rvCaracteProcedimientoInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosAllanamientoList);
        binding.rvAllanamientoInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportEdicion.this, datosParentescoList);
        binding.rvEntrevistadoInfo.setAdapter(adapter);
    }
}
