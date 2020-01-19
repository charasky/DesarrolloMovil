package org.lapoderosa.app;

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
            ejecutarServicio("http://192.168.0.8/proyecto/validar_email.php");
            //ejecutarServicio("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/validar_email.php");
        } else {
            Toast.makeText(RecuperarCuentaActivity.this, "Ingrese email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usuario", email);
    }

    @Override
    protected void inicializarStringVariables() {
        email = rEmail.getText().toString();
    }

    @Override
    protected void responseConexion(String response) {
        if (!response.isEmpty()) {
            //TODO revisar que es lo que responde
            Toast.makeText(RecuperarCuentaActivity.this, "Revise su casilla de Email", Toast.LENGTH_SHORT).show();
            enviarEmail(email);
            volverLogin();
        } else {
            Toast.makeText(RecuperarCuentaActivity.this, "No hay cuenta registrada con ese Email", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarEmail(String email) {
        //Todo rellenar para que recuperen la cuenta
    }
}
