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
    EditText mNombres, mApellidos, mEmail, mPassword, mPassword2, mAsamblea;
    Button mRegistrarBtn;
    TextView mLogin;
    ProgressBar progressBar;

    String str_asamblea, str_name, str_apellido, str_email, str_password, str_password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAsamblea = findViewById(R.id.dmAsamblea);
        mNombres = findViewById(R.id.dmName);
        mApellidos = findViewById(R.id.dmApellido);
        mEmail = findViewById(R.id.dmEmail);
        mPassword = findViewById(R.id.dmPassword1);
        mPassword2 = findViewById(R.id.dmPassword2);

        mRegistrarBtn = findViewById(R.id.dmRegistrarseBtn);
        mLogin = findViewById(R.id.dmLogin);
        progressBar = findViewById(R.id.progressBar);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/registrar_usuario.php");

            }
        });
    }

    public void moverALogin() {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usu_usuario", mEmail.getText().toString());
                parametros.put("usu_password", mPassword.getText().toString());
                parametros.put("usu_nombres", mNombres.getText().toString());
                parametros.put("usu_apellidos", mApellidos.getText().toString());
                parametros.put("usu_asamblea", mAsamblea.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        moverALogin();
    }

}

