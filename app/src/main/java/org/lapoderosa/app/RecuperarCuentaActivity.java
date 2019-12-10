package org.lapoderosa.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecuperarCuentaActivity extends AppCompatActivity {

    Button rBtSiguiente;
    TextView rLogin;
    EditText rEmail;
    String email;

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
                email = rEmail.getText().toString();

                if (!email.isEmpty()) {
                    ejecutarServicio("ingrese url :v");
                } else {
                    Toast.makeText(RecuperarCuentaActivity.this, "ingrese email", Toast.LENGTH_SHORT).show();
                }
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
                Toast.makeText(getApplicationContext(), "revise su casilla de email", Toast.LENGTH_SHORT).show();
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
                //todo agregar el php y verificar o modificar variable
                parametros.put("usu_usuario", rEmail.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        volverLogin();
    }
}
