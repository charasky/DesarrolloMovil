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
    EditText mNombre, mApellido, mEmail, mPassword, mPassword2, mAsamblea;
    Button mRegistrarBtn;
    TextView mLogin;
    ProgressBar progressBar;

    String str_asamblea, str_name, str_apellido, str_email, str_password, str_password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAsamblea = findViewById(R.id.mAsamblea);
        mNombre = findViewById(R.id.mName);
        mApellido = findViewById(R.id.mApellido);
        mEmail = findViewById(R.id.mEmail);
        mPassword = findViewById(R.id.mPassword);
        mPassword2 = findViewById(R.id.mPassword2);

        mRegistrarBtn = findViewById(R.id.mRegistrarseBtn);
        mLogin = findViewById(R.id.mLogin);
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
                Toast.makeText(getApplicationContext(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usu_nombre", mNombre.getText().toString());
                parametros.put("usu_asamblea", mAsamblea.getText().toString());
                parametros.put("usu_apellidos", mApellido.getText().toString());
                parametros.put("usu_usuario", mEmail.getText().toString());
                parametros.put("usu_password", mPassword.getText().toString());
                parametros.put("usu_validacion", "0");
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        moverALogin();
    }

}

