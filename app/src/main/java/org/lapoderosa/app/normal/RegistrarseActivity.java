package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.Check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarseActivity extends MasterClass {
    private EditText dmNombres, dmApellidos, dmEmail, dmAsamblea;
    private TextInputLayout dmPassword, dmPassword2;
    private Button dmRegistrarBtn;
    private TextView dmLogin;
    //private String registracionFecha, registracionHora;
    private String name, surname, email, asamblea, password1, password2;
    private Check check = new Check();
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
                onBackPressed();
            }
        });

        dmRegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void registrar() {
        inicializarStringVariables();
        if (!validate() && !this.checkVariables().isEmpty()) {
            Toast.makeText(this, "Revise los campos", Toast.LENGTH_SHORT).show();
        } else {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_SIGN_UP));
        }
    }

    protected void inicializarStringVariables() {
        //registracionFecha = DateDefinido.getFechaDispositivo();
        //registracionHora = DateDefinido.getHoraDispositivo();

        email = dmEmail.getText().toString().trim();
        password2 = dmPassword2.getEditText().getText().toString().trim();
        password1 = dmPassword.getEditText().getText().toString().trim();
        name = dmNombres.getText().toString().trim();
        surname = dmApellidos.getText().toString().trim();
        asamblea = dmAsamblea.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            mensaje = jsonObject.getString("existe");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mensaje.equals("asamblea")) {
            dmAsamblea.setError("Ingrese asamblea existente");
        }
        if (mensaje.equals("email")) {
            dmEmail.setError("Este email ya existe");
        }
        if (mensaje.isEmpty()) {
            startActivity(new Intent(RegistrarseActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("registracion_usuario", registracionObject().toString());
        return parametros;
    }

    private JSONObject registracionObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("usu_usuario", email);
            object.put("usu_password", password1);
            object.put("usu_nombres", name);
            object.put("usu_apellidos", surname);
            object.put("usu_asamblea", asamblea);
            //object.put("registracion_fecha", registracionFecha);
            //object.put("registracion_hora", registracionHora);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Boolean> checkVariables() {
        check.addListToCheck(check.isStringEmpty(name, dmNombres, "Ingrese nombre"));
        check.addListToCheck(check.isStringEmpty(surname, dmApellidos, "Ingrese apellido"));
        check.addListToCheck(check.isStringEmpty(asamblea, dmAsamblea, "Ingrese asamblea"));
        check.addListToCheck(check.isStringEmpty(password1, dmPassword, "Ingrese contraseña"));
        check.addListToCheck(check.isStringEmpty(password2, dmPassword2, "Reingrese contraseña"));
        return check.finalValidation();
    }

    private boolean validate() {
        boolean valid = true;

        if (!isValidEmail(email)) {
            dmEmail.setError("Ingrese email valido");
            valid = false;
        }

        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            dmPassword.setError("la contraseña debe ser mayor a 8 digitos");
            dmPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }

        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            dmPassword.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            dmPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }

        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            dmPassword.setError("la contraseña debe contener almenos un numero");
            dmPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }

        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra minuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }

        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra mayuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }

        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            dmPassword.setError("Contraseña no debe contener espacios");
            dmPassword2.setError("Contraseña no debe contener espacios");
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