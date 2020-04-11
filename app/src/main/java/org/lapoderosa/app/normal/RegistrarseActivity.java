package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityRegistrarseBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.Check;
import org.lapoderosa.app.util.MyAnimation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegistrarseActivity extends MasterClass {
    private String name, surname, email, asamblea, password1, password2;
    private ActivityRegistrarseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarseBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        progressDialog = new ProgressDialog(this);

        binding.dmRegistrarseBtn.setOnClickListener(view -> {
            MyAnimation.blink(view, this);
            registrar();
        });

        binding.layoutRegistrarse.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    protected void registrar() {
        inicializarVariables();
        if (!validate() && !this.checkVariables().isEmpty()) {
            makeTxt("Revise los campos", RegistrarseActivity.this);
        } else {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_SIGN_UP));
        }
    }

    protected void inicializarVariables() {
        email = binding.dmEmail.getText().toString().trim();
        password2 = Objects.requireNonNull(binding.dmPassword2.getEditText()).getText().toString().trim();
        password1 = Objects.requireNonNull(binding.dmPassword1.getEditText()).getText().toString().trim();
        name = binding.dmName.getText().toString().trim();
        surname = binding.dmApellido.getText().toString().trim();
        asamblea = binding.dmAsamblea.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            makeTxt(jsonObject.getString("message"), RegistrarseActivity.this);
            mensaje = jsonObject.getString("existe");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mensaje.equals("asamblea")) {
            binding.dmAsamblea.setError("Ingrese asamblea existente");
        }
        if (mensaje.equals("email")) {
            binding.dmEmail.setError("Este email ya existe");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private List<Boolean> checkVariables() {
        Check check = new Check();
        check.addListToCheck(check.isStringEmpty(name, binding.dmName, "Ingrese nombre"));
        check.addListToCheck(check.isStringEmpty(surname, binding.dmApellido, "Ingrese apellido"));
        check.addListToCheck(check.isStringEmpty(asamblea, binding.dmAsamblea, "Ingrese asamblea"));
        check.addListToCheck(check.isStringEmpty(password1, binding.dmPassword1, "Ingrese contraseña"));
        check.addListToCheck(check.isStringEmpty(password2, binding.dmPassword2, "Reingrese contraseña"));
        return check.finalValidation();
    }

    private boolean validate() {
        boolean valid = true;

        if (!isValidEmail(email)) {
            binding.dmEmail.setError("Ingrese email valido");
            valid = false;
        }

        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            binding.dmPassword1.setError("la contraseña debe ser mayor a 8 digitos");
            binding.dmPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }

        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            binding.dmPassword1.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            binding.dmPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }

        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            binding.dmPassword1.setError("la contraseña debe contener almenos un numero");
            binding.dmPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }

        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            binding.dmPassword1.setError("Contraseña debe contener almenos una letra minuscula");
            binding.dmPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }

        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            binding.dmPassword1.setError("Contraseña debe contener almenos una letra mayuscula");
            binding.dmPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }

        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            binding.dmPassword1.setError("Contraseña no debe contener espacios");
            binding.dmPassword2.setError("Contraseña no debe contener espacios");
            valid = false;
        }

        if (!password1.equals(password2)) {
            binding.dmPassword1.setError("Contraseñas no coinciden");
            //dmPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }

    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}