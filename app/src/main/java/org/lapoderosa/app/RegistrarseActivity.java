package org.lapoderosa.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
    String name, surname, email, password1, password2, asamblea;

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

        this.inicializarVariables();

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
                registrar();
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
                putParams(parametros);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        volverLogin();
    }

    protected void registrar() {
        inicializarVariables();
        if (!validate()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio("http://ec2-3-136-55-99.us-east-2.compute.amazonaws.com/proyecto/registrar_usuario.php");
        }
    }

    protected void inicializarVariables() {
        email = dmEmail.getText().toString();
        password2 = dmPassword2.getText().toString();
        password1 = dmPassword.getText().toString();
        name = dmNombres.getText().toString();
        surname = dmApellidos.getText().toString();
        asamblea = dmAsamblea.getText().toString();
    }

    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        inicializarVariables();
        parametros.put("usu_usuario", email);
        parametros.put("usu_password", password1);
        parametros.put("usu_nombres", name);
        parametros.put("usu_apellidos", surname);
        parametros.put("usu_asamblea", asamblea);
        parametros.put("usu_validacion", "FALSE");
        parametros.put("usu_administrador", "FALSE");
    }

    private boolean validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() > 12) {
            dmNombres.setError("Ingrese nombre");
            valid = false;
        }
        if (surname.isEmpty() || surname.length() > 12) {
            dmApellidos.setError("Ingrese apellido");
            valid = false;
        }
        if (asamblea.isEmpty() || surname.length() > 10) {
            dmAsamblea.setError("Ingrese asamblea");
            valid = false;
        }
        if (!isValidEmail(email)) {
            dmEmail.setError("Ingrese email valido");
            valid = false;
        }
        //todo PASSWORD
        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            dmPassword.setError("la contraseña debe ser mayor a 8 digitos");
            dmPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }
        //TODO CONTRASEÑA CARACTER ESPECIAL
        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            dmPassword.setError("la contrasela debe contener un caracter especial: !@#$%^&*+=?-");
            dmPassword2.setError("la contrasela debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }
        //TODO CONTRASEÑA DEBE TENER ALMENOS 1 NUMERO
        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            dmPassword.setError("la contraseña debe contener almenos un numero");
            dmPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }
        //TODO DEBE TENER UNA LETRA MINUSCULA
        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra minuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }
        //TODO DEBE TENER UNA LETRA MAYUSCULA
        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra mayuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }
        //TODO NO DEBE TENER ESPACIOS
        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            dmPassword.setError("Contraseña no debe contener espacios");
            dmPassword2.setError("Contraseña no debe contener espacios");
            valid = false;
        }
        if (password1.isEmpty()) {
            dmPassword.setError("Ingrese contraseña");
            valid = false;
        }
        if (password2.isEmpty()) {
            dmPassword2.setError("Reingrese contraseña");
            valid = false;
        }
        if (!password1.equals(password2)) {
            dmPassword.setError("Contraseñas no coinciden");
            dmPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }

    private final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

