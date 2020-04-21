package org.lapoderosa.app.normal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityReporteAnonimoBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.Check;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.MyAnimation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReporteAnonimo extends MasterClass {
    private String fechaCreacionReporteAnonimo, horaCreacionReporteAnonimo;
    private String emailAnonimo, celularAnonimo, barrioAnonimo, provinciaAnonimo, paisAnonimo, anonimoDetalle, fechaHechoAnonimo, horaHechoAnonimo;
    private ActivityReporteAnonimoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReporteAnonimoBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);

        binding.tvFechaAnonimo.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(ReporteAnonimo.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (v1, year1, month1, dayOfMonth) -> binding.tvFechaAnonimo.setText(dayOfMonth + "-" + (month1 + 1) + "-" + year1), year, month, day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        binding.tvHoraAnonimo.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(ReporteAnonimo.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (v2, hourOfDay, minute) -> binding.tvHoraAnonimo.setText(hourOfDay + ":" + minute), 0, 0, true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        binding.btnEnviarDA.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            enviarDenunciaAnonima();
        });
    }

    private void enviarDenunciaAnonima() {
        this.inicializarVariables();
        if (!validate() && !this.checkVariables().isEmpty()) {
            makeTxt("Revise los campos",ReporteAnonimo.this);
        } else {
            this.ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_REPORTE_ANONIMO));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private List<Boolean> checkVariables() {
        Check check = new Check();
        check.addListToCheck(check.isStringEmpty(barrioAnonimo, binding.edtBarrioAnonimo, "Ingrese barrio"));
        check.addListToCheck(check.isStringEmpty(provinciaAnonimo, binding.edtProvinciaAnonimo, "Ingrese provincia"));
        check.addListToCheck(check.isStringEmpty(paisAnonimo, binding.edtPaisAnonimo, "Ingrese pais"));
        check.addListToCheck(check.isStringEmpty(anonimoDetalle, binding.textAnonimoDetalle, "Ingrese detalle"));
        check.addListToCheck(check.isStringEmpty(fechaHechoAnonimo, binding.tvFechaAnonimo, "Ingrese fecha del hecho", this));
        check.addListToCheck(check.isStringEmpty(horaHechoAnonimo, binding.tvHoraAnonimo, "Ingrese hora del hecho", this));
        return check.finalValidation();
    }

    private boolean validate() {
        boolean valid = true;
        if (emailAnonimo.isEmpty() || celularAnonimo.isEmpty()) {
            makeTxt("Se necesita almenos email o celular",ReporteAnonimo.this);
            valid = false;
        }
        return valid;
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea salir?")
                .setCancelable(true)
                .setPositiveButton("Si", (dialog, which) -> {
                    ReporteAnonimo.this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    return;
                })

                .setNegativeButton("No", (dialog, i) -> dialog.cancel());
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
        parametros.put("fecha_reporte_anonimo_creacion", fechaCreacionReporteAnonimo);
        parametros.put("hora_reporte_anonimo_creacion", horaCreacionReporteAnonimo);
        parametros.put("reporte_anonimo", this.objectReporteAnonimo().toString());
        return parametros;
    }

    @Override
    protected void inicializarVariables() {
        fechaCreacionReporteAnonimo = DateDefinido.getFechaDispositivo();
        horaCreacionReporteAnonimo = DateDefinido.getHoraDispositivo();

        emailAnonimo = binding.edtEmailAnonimo.getText().toString().trim();
        celularAnonimo = binding.edtCelularAnonimo.getText().toString().trim();
        barrioAnonimo = binding.edtBarrioAnonimo.getText().toString().trim();
        provinciaAnonimo = binding.edtProvinciaAnonimo.getText().toString().trim();
        paisAnonimo = binding.edtPaisAnonimo.getText().toString().trim();
        anonimoDetalle = binding.textAnonimoDetalle.getEditText().getText().toString().trim();
        fechaHechoAnonimo = binding.tvFechaAnonimo.getText().toString().trim();
        horaHechoAnonimo = binding.tvHoraAnonimo.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        boolean validate = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            makeTxt(jsonObject.getString("message"),ReporteAnonimo.this);
            validate = jsonObject.getBoolean("validate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(validate){
            startActivity(new Intent(ReporteAnonimo.this,HomeActivity.class));
            finish();
        }
    }
}
