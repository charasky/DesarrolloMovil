package org.lapoderosa.app.normal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.Check;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReporteAnonimo extends MasterClass {
    private String fechaCreacionReporteAnonimo, horaCreacionReporteAnonimo;
    private Button btnEnviarDA;
    private String emailAnonimo, celularAnonimo, barrioAnonimo, provinciaAnonimo, paisAnonimo, anonimoDetalle, fechaHechoAnonimo, horaHechoAnonimo;
    private EditText edtEmailAnonimo, edtCelularAnonimo, edtBarrioAnonimo, edtProvinciaAnonimo, edtPaisAnonimo;
    private TextInputLayout textAnonimoDetalle, tilTextDetalle;
    private TextView tvFechaAnonimo, tvHoraAnonimo;
    private Check check = new Check();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reporte_anonimo);
        progressDialog = new ProgressDialog(this);
        btnEnviarDA = findViewById(R.id.btnEnviarDA);

        edtEmailAnonimo = findViewById(R.id.edtEmailAnonimo);
        edtCelularAnonimo = findViewById(R.id.edtCelularAnonimo);

        tvFechaAnonimo = findViewById(R.id.tvFechaAnonimo);
        tvHoraAnonimo = findViewById(R.id.tvHoraAnonimo);

        edtBarrioAnonimo = findViewById(R.id.edtBarrioAnonimo);
        edtProvinciaAnonimo = findViewById(R.id.edtProvinciaAnonimo);
        edtPaisAnonimo = findViewById(R.id.edtPaisAnonimo);
        textAnonimoDetalle = findViewById(R.id.textAnonimoDetalle);


        tvFechaAnonimo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ReporteAnonimo.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tvFechaAnonimo.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        tvHoraAnonimo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(ReporteAnonimo.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHoraAnonimo.setText(hourOfDay + ":" + minute);
                            }
                        }, 0, 0, true);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        btnEnviarDA.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                enviarDenunciaAnonima();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void enviarDenunciaAnonima() {
        this.inicializarStringVariables();
        if (this.checkVariables().isEmpty()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            this.ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_REPORTE_ANONIMO));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Boolean> checkVariables() {
        check.addListToCheck(check.isStringEmpty(emailAnonimo, edtEmailAnonimo, "Ingrese email"));
        check.addListToCheck(check.isStringEmpty(celularAnonimo, edtCelularAnonimo, "Ingrese celular"));
        check.addListToCheck(check.isStringEmpty(barrioAnonimo, edtBarrioAnonimo, "Ingrese barrio"));
        check.addListToCheck(check.isStringEmpty(provinciaAnonimo, edtProvinciaAnonimo, "Ingrese provincia"));
        check.addListToCheck(check.isStringEmpty(paisAnonimo, edtPaisAnonimo, "Ingrese pais"));
        check.addListToCheck(check.isStringEmpty(anonimoDetalle, textAnonimoDetalle, "Ingrese detalle"));
        check.addListToCheck(check.isStringEmpty(fechaHechoAnonimo, tvFechaAnonimo, "Ingrese fecha del hecho", this));
        check.addListToCheck(check.isStringEmpty(horaHechoAnonimo, tvHoraAnonimo, "Ingrese hora del hecho", this));
        return check.finalValidation();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea salir?")
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReporteAnonimo.this.finish();
                        progressDialog.dismiss();
                        return;
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private JSONObject objectReporteAnonimo() {
        JSONObject object = new JSONObject();
        try {
            object.put("usu_email_anonimo", emailAnonimo);
            object.put("usu_celular_anonimo", celularAnonimo);
            object.put("usu_barrio_anonimo", barrioAnonimo);
            object.put("usu_provincia_anonimo", provinciaAnonimo);
            object.put("usu_pais_anonimo", paisAnonimo);
            object.put("usu_detalle_anonimo", anonimoDetalle);
            object.put("usu_fecha_hecho_anonimo", fechaHechoAnonimo);
            object.put("usu_hora_hecho_anonimo", horaHechoAnonimo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("fechaReporte", fechaCreacionReporteAnonimo);
        parametros.put("horaReporte", horaCreacionReporteAnonimo);
        parametros.put("reporte", this.objectReporteAnonimo().toString());
        return parametros;
    }

    @Override
    protected void inicializarStringVariables() {
        fechaCreacionReporteAnonimo = DateDefinido.getFechaDispositivo();
        horaCreacionReporteAnonimo = DateDefinido.getHoraDispositivo();

        emailAnonimo = edtEmailAnonimo.getText().toString().trim();
        celularAnonimo = edtCelularAnonimo.getText().toString().trim();
        barrioAnonimo = edtBarrioAnonimo.getText().toString().trim();
        provinciaAnonimo = edtProvinciaAnonimo.getText().toString().trim();
        paisAnonimo = edtPaisAnonimo.getText().toString().trim();
        anonimoDetalle = textAnonimoDetalle.getEditText().getText().toString().trim();
        fechaHechoAnonimo = tvFechaAnonimo.getText().toString().trim();
        horaHechoAnonimo = tvHoraAnonimo.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {

    }
}
