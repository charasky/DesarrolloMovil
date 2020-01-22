package org.lapoderosa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.admin.SharedPrefManager;

import java.util.Map;

public class RecuperarCuentaActivity extends MasterClass {

    private Button rBtSiguiente;
    private TextView rLogin;
    private EditText rEmail;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcuenta);

        rBtSiguiente = findViewById(R.id.rBtSiguiente);
        rLogin = findViewById(R.id.rLogin);
        rEmail = findViewById(R.id.rEmail);
        progressDialog = new ProgressDialog(this);

        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecuperarCuentaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        rBtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperar();
            }
        });
    }

    private void recuperar() {
        inicializarStringVariables();
        if (!email.isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.URL_ACCOUNT_RECOVERY));
        } else {
            Toast.makeText(RecuperarCuentaActivity.this, "Ingrese email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_usuario", email);
    }

    @Override
    protected void inicializarStringVariables() {
        email = rEmail.getText().toString();
    }

    @Override
    protected void responseConexion(String response) {
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
            mensaje = jsonObject.getString("existe");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(mensaje.equals("0")){
            rEmail.setError("Este email no existe");
        }else{
            volverLogin();
        }
    }

    private void enviarEmail(String email) {
        //Todo rellenar para que recuperen la cuenta
    }
}
