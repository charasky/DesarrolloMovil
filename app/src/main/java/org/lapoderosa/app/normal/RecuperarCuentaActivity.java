package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.google.android.material.textfield.TextInputLayout;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.JavaMailAPI;

import java.util.HashMap;
import java.util.Map;

public class RecuperarCuentaActivity extends MasterClass {
    private Button rBtSiguiente;
    private TextView rLogin;
    private TextInputLayout rEmail;
    private String email;
    private RelativeLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcuenta);
        progressDialog = new ProgressDialog(this);

        layout = findViewById(R.id.layoutRecuperarCuenta);
        rBtSiguiente = findViewById(R.id.rBtSiguiente);
        rLogin = findViewById(R.id.rLogin);
        rEmail = findViewById(R.id.rEmail);

        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rBtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
                animation.setDuration(500);
                rBtSiguiente.setAlpha(1f);
                rBtSiguiente.startAnimation(animation);
                recuperar();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    private void recuperar() {
        inicializarStringVariables();
        if (!email.isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_ACCOUNT_RECOVERY));
        } else {
            Toast.makeText(RecuperarCuentaActivity.this, "Ingrese email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Map<String, String> putParams(){
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_usuario", email);
        return parametros;
    }

    @Override
    protected void inicializarStringVariables() {
        email = rEmail.getEditText().getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            mensaje = jsonObject.getString("existe");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mensaje.equals("0")) {
            rEmail.setError("Este email no existe");
        } else {
            enviarEmail(email);
            startActivity(new Intent(RecuperarCuentaActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void enviarEmail(String email) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email);
        javaMailAPI.execute();
    }
}
