package org.lapoderosa.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

public class ReporteActivity extends AppCompatActivity {


    private static final String TAG = "ReporteActivity";
    private Utilidades utilidades = new Utilidades();

    private TextView tvDate1,tvDate2,tvHora1;
    private DatePickerDialog.OnDateSetListener m1DateSetListener;
    private DatePickerDialog.OnDateSetListener m2DateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button guardar;
    //ENTREVISTADOR
    private EditText edtNombreEntrevistador,edtApellidoEntrevistador,edtAsamblea;
    //ENTREVISTADO
    private EditText otroParentesco;
    private RadioButton rbtVictima, rbtFamiliar;
    private String parentesco;
    //VICTIMA
    private EditText edtNombreVictima,edtApellidoVictima,edtGeneroVictima,edtEdadVictima,edtNacionalidadVictima,edtDocumentoVictima,edtDireccionVictima,edtTelefonoVictima;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        tvDate2 = findViewById(R.id.tvDateHecho);
        guardar = findViewById(R.id.btnGuardar);

        //ENTREVISTADOR
        edtNombreEntrevistador = findViewById(R.id.edtNombreEntrevistador);
        edtApellidoEntrevistador = findViewById(R.id.edtApellidoEntrevistador);
        edtAsamblea = findViewById(R.id.edtAsamblea);
        tvDate1 = findViewById(R.id.tvDateEntrevista);

        //ENTREVISTADO

        rbtVictima = findViewById(R.id.rbtVictima);
        rbtVictima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtFamiliar.setChecked(false);
            }
        });

        rbtFamiliar = findViewById(R.id.rbtFamiliar);

        rbtFamiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtVictima.setChecked(false);
            }
        });

        otroParentesco = findViewById(R.id.edtOtroParentesco);



        parentesco = utilidades.parentesco(rbtVictima,rbtFamiliar,otroParentesco);

        //VICTIMA

        edtNombreVictima = findViewById(R.id.edtNombreVictima);
        edtApellidoVictima = findViewById(R.id.edtApellidoVictima);
        edtGeneroVictima = findViewById(R.id.edtGenero);
        edtEdadVictima = findViewById(R.id.edtEdad);
        edtNacionalidadVictima = findViewById(R.id.edtNacionalidad);
        edtDocumentoVictima = findViewById(R.id.edtDocumento);
        edtDireccionVictima = findViewById(R.id.edtDireccionVictima);
        edtTelefonoVictima = findViewById(R.id.edtTelefono);


        tvDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        m1DateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        m1DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                tvDate1.setText(date);
            }
        };

        tvDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        m2DateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        m2DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                tvDate2.setText(date);
            }
        };

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicioEntrevistador("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/insertar_datos_entrevistador.php");
                servicioEntrevistado("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/insertar_datos_entrevistado.php");
            }
        });

        /*tvHora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time time = new Time();
            }
        });*/

    }

    private void servicioEntrevistado(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Operacion Exitosa",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("usu_parentesco",parentesco);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void servicioEntrevistador(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Operacion Exitosa",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("usu_parentesco",edtNombreEntrevistador.getText().toString());
                parametros.put("usu_apellido",edtApellidoEntrevistador.getText().toString());
                parametros.put("usu_asamblea",edtAsamblea.getText().toString());
                parametros.put("usu_fecha",tvDate1.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
