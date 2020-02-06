package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;

import java.util.Map;

public class RegistrarseActivity extends MasterClass {
    private EditText dmNombres, dmApellidos, dmEmail, dmPassword, dmPassword2, dmAsamblea;
    private Button dmRegistrarBtn;
    private TextView dmLogin;
    private String name, surname, email, password1, password2, asamblea;
    private RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        progressDialog = new ProgressDialog(this);

        layout = findViewById(R.id.layoutRegistrarse);
        dmAsamblea = findViewById(R.id.dmAsamblea);
        dmNombres = findViewById(R.id.dmName);
        dmApellidos = findViewById(R.id.dmApellido);
        dmEmail = findViewById(R.id.dmEmail);
        dmPassword = findViewById(R.id.dmPassword1);
        dmPassword2 = findViewById(R.id.dmPassword2);
        dmRegistrarBtn = findViewById(R.id.dmRegistrarseBtn);
        dmLogin = findViewById(R.id.dmLogin);

        dmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irLogin();
            }
        });

        dmRegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }

    protected void registrar() {
        inicializarStringVariables();
        if (!validate()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.URL_SIGN_UP));
        }
    }

    protected void inicializarStringVariables() {
        email = dmEmail.getText().toString().trim();
        password2 = dmPassword2.getText().toString().trim();
        password1 = dmPassword.getText().toString().trim();
        name = dmNombres.getText().toString().trim();
        surname = dmApellidos.getText().toString().trim();
        asamblea = dmAsamblea.getText().toString().trim();
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

        if(mensaje.equals("asamblea")){
            dmAsamblea.setError("Ingrese asamblea existente");
        }
        if(mensaje.equals("email")){
            dmEmail.setError("Este email ya existe");
        }
        if(mensaje.isEmpty()){
            irLogin();
        }
    }

    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_usuario", email);
        parametros.put("usu_password", password1);
        parametros.put("usu_nombres", name);
        parametros.put("usu_apellidos", surname);
        parametros.put("usu_asamblea", asamblea);
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
        //PASSWORD contraseña con 8 caracteres
        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            dmPassword.setError("la contraseña debe ser mayor a 8 digitos");
            dmPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }
        //CONTRASEÑA CARACTER ESPECIAL
        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            dmPassword.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            dmPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }
        //CONTRASEÑA DEBE TENER ALMENOS 1 NUMERO
        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            dmPassword.setError("la contraseña debe contener almenos un numero");
            dmPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }
        //DEBE TENER UNA LETRA MINUSCULA
        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra minuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }
        //DEBE TENER UNA LETRA MAYUSCULA
        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra mayuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }
        //NO DEBE TENER ESPACIOS
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
            //dmPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }

    private final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}