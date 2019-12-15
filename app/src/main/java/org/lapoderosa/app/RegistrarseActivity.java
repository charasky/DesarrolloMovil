package org.lapoderosa.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lapoderosa.app.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {
    EditText dmNombres, dmApellidos, dmEmail, dmPassword, dmPassword2, dmAsamblea;
    Button dmRegistrarBtn;
    TextView dmLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        dmAsamblea = findViewById(R.id.dmAsamblea);
        dmNombres = findViewById(R.id.dmName);
        dmApellidos = findViewById(R.id.dmApellido);
        dmEmail = findViewById(R.id.dmEmail);
        dmPassword = findViewById(R.id.dmPassword1);
        dmPassword2 = findViewById(R.id.dmPassword2);

        dmRegistrarBtn = findViewById(R.id.dmRegistrarseBtn);
        dmLogin = findViewById(R.id.dmLogin);
        //progressBar = findViewById(R.id.progressBar);

        dmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        dmRegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/registrar_usuario.php");
            }
        });
    }

    public void volverLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Nueva cuenta con exito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usu_usuario", dmEmail.getText().toString());
                parametros.put("usu_password", dmPassword.getText().toString());
                parametros.put("usu_nombres", dmNombres.getText().toString());
                parametros.put("usu_apellidos", dmApellidos.getText().toString());
                parametros.put("usu_asamblea", dmAsamblea.getText().toString());
                parametros.put("usu_validacion", "FALSE");
                parametros.put("usu_administrador", "FALSE");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        volverLogin();
    }

    private void verificaciones(String asamble, String nombre, String apellido, String email, String contraseña1, String contraseña2) {
        //todo agregar las condiciones para cada campo
    }
}

