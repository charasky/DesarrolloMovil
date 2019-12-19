package org.lapoderosa.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import java.util.Map;

public class LoginActivity extends MasterClass {
    private EditText editUsuario, editPassword;
    private TextView etRegistrarse, etOlvidastesContraseña;
    private Button btnLogin;
    private String usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.etUserName);
        editPassword = findViewById(R.id.dmPassword1);
        etRegistrarse = findViewById(R.id.etRegistrarse);
        btnLogin = findViewById(R.id.btLogin);
        etOlvidastesContraseña = findViewById(R.id.etOlvidastesContraseña);

        recuperarPreferencias();

        etOlvidastesContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarCuentaActivity.class);
                startActivity(intent);
            }
        });

        etRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
            }
        });
    }

    private void iniciar(){
        inicializarStringVariables();
        if (!usuario.isEmpty() && !password.isEmpty()) {
            ejecutarServicio("http://3.136.55.99/proyecto/validar_usuario.php");
        } else {
            Toast.makeText(LoginActivity.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    private void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        editUsuario.setText(preferences.getString("usuario", ""));
        editPassword.setText(preferences.getString("password", ""));
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usuario", usuario);
        parametros.put("password", password);
    }

    @Override
    protected void inicializarStringVariables() {
        usuario = editUsuario.getText().toString();
        password = editPassword.getText().toString();
    }

    @Override
    protected void responseConexion(String response) {
        if (!response.isEmpty()) {
            guardarPreferencias();
            Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}

