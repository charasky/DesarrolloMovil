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
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.adapter.InfoAdapter;
import org.lapoderosa.app.model.Informacion;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.SharedPrefManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportVisualizacion extends MasterClass implements View.OnClickListener {
    private static final int STORAGE_PERMISSION_CODE = 101;
    private TextView fullNameVictima, provincia, pais, hora, fecha;
    private EditText ruvParentesco;
    private String id;
    private Button btnPdf, btnGuardar;
    private ImageView pdfIcon;
    private RecyclerView rvVicInfo, rvTrasladoInfo, rvResulInvesInfo, rvOmisionActuarInfo, rvModalidadDetencionInfo, rvHechoPolicialInfo, rvFuerzasIntervinientesInfo, rvEntrevistadorInfo, rvCaracteProcedimientoInfo, rvAllanamientoInfo;
    private InfoAdapter adapter;
    private List<Informacion> datosVicList, datosTrasladoList, datosResultadoList, datosOmisionActuarList, datosModalidadDetencionList, datosHechoPolicialList, datosFuerzasIntervinientesList, datosEntrevistadorList, datosCaracteProcedimiento, datosAllanamientoList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualizacion);
        progressDialog = new ProgressDialog(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        inicializarVariables();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            fullNameVictima.setText(bundle.getString("name"));
            provincia.setText(bundle.getString("provincia"));
            pais.setText(bundle.getString("pais"));
            hora.setText(bundle.getString("hora"));
            fecha.setText(bundle.getString("fecha"));
        }

        rvLyManager();

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_CONSEGUIR_REPORTE));

        myAdapters();
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

        findViews();
        myOnClicks();
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObjectInfo = new JSONObject(response);

            Log.e("info", "json:" + jsonObjectInfo);
            datosVicList.add(new Informacion("Nombre:", jsonObjectInfo.getString("usu_nombre_victima"), "usu_nombre_victima", true));
            datosVicList.add(new Informacion("Apellido:", jsonObjectInfo.getString("usu_apellido_victima"), "usu_apellido_victima",true));
            datosVicList.add(new Informacion("Genero:", jsonObjectInfo.getString("usu_genero_victima"), "usu_genero_victima",true));
            datosVicList.add(new Informacion("Edad:", jsonObjectInfo.getString("usu_edad_victima"), "usu_edad_victima",true));
            datosVicList.add(new Informacion("Nacionalidad:", jsonObjectInfo.getString("usu_nacionalidad_victima"), "usu_nacionalidad_victima",true));
            datosVicList.add(new Informacion("Documento:", jsonObjectInfo.getString("usu_documento_victima"), "usu_documento_victima",true));
            datosVicList.add(new Informacion("Direccion:", jsonObjectInfo.getString("usu_direccion_victima"), "usu_direccion_victima",true));
            datosVicList.add(new Informacion("Barrio:", jsonObjectInfo.getString("usu_barrio_victima"), "usu_barrio_victima",true));
            datosVicList.add(new Informacion("Nombre:", jsonObjectInfo.getString("usu_telefono_victima"), "usu_telefono_victima",true));

            datosTrasladoList.add(new Informacion("Fue Traslado:", jsonObjectInfo.getString("usu_traslado"), "usu_traslado",true));
            datosTrasladoList.add(new Informacion("Cual Comisaria:", jsonObjectInfo.getString("usu_comisaria"), "usu_comisaria",true));
            datosTrasladoList.add(new Informacion("Fue Esposado:", jsonObjectInfo.getString("usu_esposado"), "usu_esposado",true));

            datosResultadoList.add(new Informacion("Resultado:", jsonObjectInfo.getString("usu_resultado_investigacion"), "usu_resultado_investigacion",true));
            datosResultadoList.add(new Informacion("Trabajan los Oficiales:", jsonObjectInfo.getString("usu_trabajan_los_oficiales"), "usu_trabajan_los_oficiales",true));

            datosOmisionActuarList.add(new Informacion("Medio de Asistencia:", jsonObjectInfo.getString("usu_medios_de_asistencia"), "usu_medios_de_asistencia",true));
            datosOmisionActuarList.add(new Informacion("Quien lo asistio:", jsonObjectInfo.getString("usu_a_quien_asistencia"), "usu_a_quien_asistencia",true));
            datosOmisionActuarList.add(new Informacion("Denuncia fue rechazada:", jsonObjectInfo.getString("usu_denuncia_rechazada"), "usu_denuncia_rechazada",true));
            datosOmisionActuarList.add(new Informacion("Fue Violentado:", jsonObjectInfo.getString("usu_violentado"), "usu_violentado",true));
            datosOmisionActuarList.add(new Informacion("Denuncia final:", jsonObjectInfo.getString("usu_denuncia_final"), "usu_denuncia_final",true));

            datosModalidadDetencionList.add(new Informacion("Posicion al ser Detenido:", jsonObjectInfo.getString("usu_posicion_detenido"), "usu_posicion_detenido",true));
            datosModalidadDetencionList.add(new Informacion("Tiempo Detenido:", jsonObjectInfo.getString("usu_tiempo_detenido"), "usu_tiempo_detenido",true));

            datosHechoPolicialList.add(new Informacion("Dia:", jsonObjectInfo.getString("usu_dia_hecho"), "usu_dia_hecho",true));
            datosHechoPolicialList.add(new Informacion("Hora:", jsonObjectInfo.getString("usu_hora_hecho"), "usu_hora_hecho",true));
            datosHechoPolicialList.add(new Informacion("Cuantos Acompañaron:", jsonObjectInfo.getString("usu_cuantos_acompanian"), "usu_cuantos_acompanian",true));
            datosHechoPolicialList.add(new Informacion("Lugar:", jsonObjectInfo.getString("usu_cual_lugar"), "usu_cual_lugar",true));
            datosHechoPolicialList.add(new Informacion("Provincia:", jsonObjectInfo.getString("usu_provincia_hecho"), "usu_provincia_hecho",true));
            datosHechoPolicialList.add(new Informacion("Pais:", jsonObjectInfo.getString("usu_pais_hecho"), "usu_pais_hecho",true));
            datosHechoPolicialList.add(new Informacion("Direccion:", jsonObjectInfo.getString("usu_direccion_hecho"), "usu_direccion_hecho",true));
            datosHechoPolicialList.add(new Informacion("Barrio:", jsonObjectInfo.getString("usu_barrio_hecho"), "usu_barrio_hecho",true));

            datosFuerzasIntervinientesList.add(new Informacion("Tipo de Policia:", jsonObjectInfo.getString("usu_fuerzas_intervinientes"), "usu_fuerzas_intervinientes",true));
            datosFuerzasIntervinientesList.add(new Informacion("Cantidad Agentes:", jsonObjectInfo.getString("usu_cantidad_agentes"), "usu_cantidad_agentes",true));
            datosFuerzasIntervinientesList.add(new Informacion("Nombre Agentes:", jsonObjectInfo.getString("usu_nombres_agentes"), "usu_nombres_agentes",true));
            datosFuerzasIntervinientesList.add(new Informacion("Apodos:", jsonObjectInfo.getString("usu_apodos"), "usu_apodos",true));
            datosFuerzasIntervinientesList.add(new Informacion("Cantidad Vehiculos:", jsonObjectInfo.getString("usu_cantidad_vehiculos"), "usu_cantidad_vehiculos",true));
            datosFuerzasIntervinientesList.add(new Informacion("Patente Vehiculos:", jsonObjectInfo.getString("usu_num_movil"), "usu_num_movil",true));
            datosFuerzasIntervinientesList.add(new Informacion("Dominio:", jsonObjectInfo.getString("usu_dominio"), "usu_dominio",true));
            datosFuerzasIntervinientesList.add(new Informacion("Conducta Agentes:", jsonObjectInfo.getString("usu_conducta_agentes"), "usu_conducta_agentes",true));

            datosCaracteProcedimiento.add(new Informacion("Motivo de Procedimiento", jsonObjectInfo.getString("usu_motivo_procedimiento"), "usu_motivo_procedimiento",true));
            datosCaracteProcedimiento.add(new Informacion("Hubo Maltratos:", jsonObjectInfo.getString("usu_maltratos"), "usu_maltratos",true));
            datosCaracteProcedimiento.add(new Informacion("Hubo Lesiones:", jsonObjectInfo.getString("usu_lesiones"), "usu_lesiones",true));

            datosAllanamientoList.add(new Informacion("Hubo Orden de Allanamiento;", jsonObjectInfo.getString("usu_orden_allanamiento"), "usu_orden_allanamiento",true));
            datosAllanamientoList.add(new Informacion("Hubo Agresion:", jsonObjectInfo.getString("usu_agresion_allanamiento"), "usu_agresion_allanamiento",true));
            datosAllanamientoList.add(new Informacion("Pertenencias Allanadas:", jsonObjectInfo.getString("usu_pertenencias_allanamiento"), "usu_pertenencias_allanamiento",true));
            datosAllanamientoList.add(new Informacion("Hubo Omision Pertenencias:", jsonObjectInfo.getString("usu_omision_pertenencias"), "usu_omision_pertenencias",true));
            datosAllanamientoList.add(new Informacion("Hubo Detenidos:", jsonObjectInfo.getString("usu_detenidos_allanamiento"), "usu_detenidos_allanamiento",true));
            datosAllanamientoList.add(new Informacion("Duracion:", jsonObjectInfo.getString("usu_duracion_allanamiento"), "usu_duracion_allanamiento",true));
            datosAllanamientoList.add(new Informacion("Fue Esposado:", jsonObjectInfo.getString("usu_esposados"), "usu_esposados",true));
            datosAllanamientoList.add(new Informacion("Posicion:", jsonObjectInfo.getString("usu_posicion_allanamiento"), "usu_posicion_allanamiento",true));

            //todo sacar esta variable unica
            ruvParentesco.setText(jsonObjectInfo.getString("usu_parentesco_entrevistado"));

            datosEntrevistadorList.add(new Informacion("Nombre:", jsonObjectInfo.getString("usu_nombre"), "usu_nombre",false));
            datosEntrevistadorList.add(new Informacion("Apellido:", jsonObjectInfo.getString("usu_apellido"), "usu_apellido",false));
            datosEntrevistadorList.add(new Informacion("Asamblea:", jsonObjectInfo.getString("usu_asamblea"), "usu_asamblea",false));
            datosEntrevistadorList.add(new Informacion("Fecha De Modificacion:", jsonObjectInfo.getString("usu_fecha"), "usu_fecha",false));

            myAdapters();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardar:

                break;
            case R.id.btPdf:
                AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
                animation.setDuration(500);
                btnPdf.setAlpha(1f);
                btnPdf.startAnimation(animation);
                break;
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
        makeTxt("creado", ReportVisualizacion.this);
    }

    private void findViews() {
        //recicledView
        rvVicInfo = findViewById(R.id.rvVicInfo);
        rvTrasladoInfo = findViewById(R.id.rvTrasladoInfo);
        rvResulInvesInfo = findViewById(R.id.rvResulInvesInfo);
        rvOmisionActuarInfo = findViewById(R.id.rvOmisionActuarInfo);
        rvModalidadDetencionInfo = findViewById(R.id.rvModalidadDetencionInfo);
        rvHechoPolicialInfo = findViewById(R.id.rvHechoPolicialInfo);
        rvFuerzasIntervinientesInfo = findViewById(R.id.rvFuerzasIntervinientesInfo);
        rvEntrevistadorInfo = findViewById(R.id.rvEntrevistadorInfo);
        rvCaracteProcedimientoInfo = findViewById(R.id.rvCaracteProcedimientoInfo);
        rvAllanamientoInfo = findViewById(R.id.rvAllanamientoInfo);

        ruvParentesco = findViewById(R.id.ruvParentesco);

        //Datos del reporte
        fullNameVictima = findViewById(R.id.vName);
        provincia = findViewById(R.id.vProvincia);
        pais = findViewById(R.id.vPais);
        hora = findViewById(R.id.vHora);
        fecha = findViewById(R.id.vFecha);

        //buttoms
        btnPdf = findViewById(R.id.btPdf);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    private void myOnClicks() {
        btnPdf.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }

    private void rvLyManager() {
        rvVicInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvTrasladoInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvResulInvesInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvOmisionActuarInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvModalidadDetencionInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvHechoPolicialInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvFuerzasIntervinientesInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvEntrevistadorInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvCaracteProcedimientoInfo.setLayoutManager(new GridLayoutManager(this, 2));
        rvAllanamientoInfo.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void myAdapters() {
        adapter = new InfoAdapter(ReportVisualizacion.this, datosVicList);
        rvVicInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosTrasladoList);
        rvTrasladoInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosResultadoList);
        rvResulInvesInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosOmisionActuarList);
        rvOmisionActuarInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosModalidadDetencionList);
        rvModalidadDetencionInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosHechoPolicialList);
        rvHechoPolicialInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosFuerzasIntervinientesList);
        rvFuerzasIntervinientesInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosEntrevistadorList);
        rvEntrevistadorInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosCaracteProcedimiento);
        rvCaracteProcedimientoInfo.setAdapter(adapter);

        adapter = new InfoAdapter(ReportVisualizacion.this, datosAllanamientoList);
        rvAllanamientoInfo.setAdapter(adapter);
    }
}
