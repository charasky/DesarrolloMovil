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
    String URL = "http://192.168.0.6/phpmyadmin/";
    EditText mNombre, mApellido, mEmail, mPassword, mPassword2;
    Button mRegistrarBtn;
    TextView mLogin;
    ProgressBar progressBar;

    String str_name, str_apellido, str_email, str_password, str_password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

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
                Registrarse(v);
                moverALogin(v);
            }
        });
    }

    public void moverALogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void Registrarse(View view) {
        if (mNombre.getText().toString().equals("")) {
            Toast.makeText(this, "ingrese nombre", Toast.LENGTH_SHORT).show();
        } else if (mApellido.getText().toString().equals("")) {
            Toast.makeText(this, "ingrese apellido", Toast.LENGTH_SHORT).show();
        } else if (mEmail.getText().toString().equals("")) {
            Toast.makeText(this, "ingrese email", Toast.LENGTH_SHORT).show();
        } else if (mPassword.getText().toString().equals("")) {
            Toast.makeText(this, "ingrese contraseña", Toast.LENGTH_SHORT).show();
        } else if (mPassword2.getText().toString().equals("")) {
            Toast.makeText(this, "confirme contraseña", Toast.LENGTH_SHORT).show();
        } else {
            str_name = mNombre.getText().toString().trim();
            str_apellido = mApellido.getText().toString().trim();
            str_email = mEmail.getText().toString().trim();
            str_password = mPassword.getText().toString().trim();
            str_password2 = mPassword2.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RegistrarseActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RegistrarseActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", str_name);
                    params.put("apellido", str_apellido);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegistrarseActivity.this);
            requestQueue.add(request);
        }
    }
}
