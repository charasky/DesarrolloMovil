package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

public class AdminRestablecerContraseñaActivity extends MasterClass {
    private EditText rPassword1, rPassword2, rEmail;
    private TextView rVolver;
    private Button rBtSiguiente;
    private String password1, password2, email;
    private RelativeLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_restablecer_cuenta);
        progressDialog = new ProgressDialog(this);

        layout = findViewById(R.id.adRestablecerContraseña);
        rEmail = findViewById(R.id.rEmail);
        rPassword1 = findViewById(R.id.rPassword1);
        rPassword2 = findViewById(R.id.rPassword2);
        rVolver = findViewById(R.id.rVolver);
        rBtSiguiente = findViewById(R.id.rBtSiguiente);

        rVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irInicioAdmin();
            }
        });

        rBtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePassword();
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

    protected void restorePassword(){
        inicializarStringVariables();
        if (!validate()) {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.URL_RESTORE_PASSWORD));
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (email.isEmpty()) {
            rEmail.setError("Ingrese email");
            valid = false;
        }

        //PASSWORD contraseña con 8 caracteres
        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            rPassword1.setError("la contraseña debe ser mayor a 8 digitos");
            rPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }
        //CONTRASEÑA CARACTER ESPECIAL
        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            rPassword1.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            rPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }
        //CONTRASEÑA DEBE TENER ALMENOS 1 NUMERO
        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            rPassword1.setError("la contraseña debe contener almenos un numero");
            rPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }
        //DEBE TENER UNA LETRA MINUSCULA
        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            rPassword1.setError("Contraseña debe contener almenos una letra minuscula");
            rPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }
        //DEBE TENER UNA LETRA MAYUSCULA
        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            rPassword1.setError("Contraseña debe contener almenos una letra mayuscula");
            rPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }
        //NO DEBE TENER ESPACIOS
        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            rPassword1.setError("Contraseña no debe contener espacios");
            rPassword2.setError("Contraseña no debe contener espacios");
            valid = false;
        }
        if (password1.isEmpty()) {
            rPassword1.setError("Ingrese contraseña");
            valid = false;
        }
        if (password2.isEmpty()) {
            rPassword2.setError("Reingrese contraseña");
            valid = false;
        }
        if (!password1.equals(password2)) {
            rPassword1.setError("Contraseñas no coinciden");
            //rPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_usuario", email);
        parametros.put("usu_password", password1);
    }

    @Override
    protected void inicializarStringVariables() {
        email = rEmail.getText().toString().trim();
        password1 = rPassword1.getText().toString().trim();
        password2 = rPassword2.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        //todo el php debe revisar si o no existe el email
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
            mensaje = jsonObject.getString("revisar");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!mensaje.isEmpty()){
            rEmail.setError("Este email ingresado no existe");
        }else {
            irInicioAdmin();
        }
    }
}
