package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityAdminRestablecerCuentaBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.MyAnimation;

import java.util.HashMap;
import java.util.Map;

public class AdminRestablecerContraseñaActivity extends MasterClass {
    private String password1, password2, email;
    private ActivityAdminRestablecerCuentaBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRestablecerCuentaBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        progressDialog = new ProgressDialog(this);

        binding.rBtSiguiente.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            restorePassword();
        });

        binding.layoutRestablecer.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    protected void restorePassword() {
        inicializarVariables();
        if (!validate()) {
            makeTxt("Complete los campos",AdminRestablecerContraseñaActivity.this);
        } else {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_RESTORE_PASSWORD));
        }
    }

    private boolean validate() {
        //todo this cheeeck validar en una clase :v unica
        boolean valid = true;
        if (email.isEmpty()) {
            binding.rEmail.setError("Ingrese email");
            valid = false;
        }

        //PASSWORD contraseña con 8 caracteres
        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            binding.rPassword1.setError("la contraseña debe ser mayor a 8 digitos");
            binding.rPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }
        //CONTRASEÑA CARACTER ESPECIAL
        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            binding.rPassword1.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            binding.rPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }
        //CONTRASEÑA DEBE TENER ALMENOS 1 NUMERO
        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            binding.rPassword1.setError("la contraseña debe contener almenos un numero");
            binding.rPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }
        //DEBE TENER UNA LETRA MINUSCULA
        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            binding.rPassword1.setError("Contraseña debe contener almenos una letra minuscula");
            binding.rPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }
        //DEBE TENER UNA LETRA MAYUSCULA
        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            binding.rPassword1.setError("Contraseña debe contener almenos una letra mayuscula");
            binding.rPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }
        //NO DEBE TENER ESPACIOS
        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            binding.rPassword1.setError("Contraseña no debe contener espacios");
            binding.rPassword2.setError("Contraseña no debe contener espacios");
            valid = false;
        }
        if (password1.isEmpty()) {
            binding.rPassword1.setError("Ingrese contraseña");
            valid = false;
        }
        if (password2.isEmpty()) {
            binding.rPassword2.setError("Reingrese contraseña");
            valid = false;
        }
        if (!password1.equals(password2)) {
            binding.rPassword1.setError("Contraseñas no coinciden");
            //rPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_usuario", email);
        parametros.put("usu_password", password1);
        return parametros;
    }

    @Override
    protected void inicializarVariables() {
        email = binding.rEmail.getText().toString().trim();
        password1 = binding.rPassword1.getText().toString().trim();
        password2 = binding.rPassword2.getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        //todo el php debe revisar si o no existe el email
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            makeTxt(jsonObject.getString("message"),AdminRestablecerContraseñaActivity.this);
            mensaje = jsonObject.getString("revisar");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!mensaje.isEmpty()) {
            binding.rEmail.setError("Este email ingresado no existe");
        } else {
            startActivity(new Intent(AdminRestablecerContraseñaActivity.this, AdminInicioActivity.class));
            finish();
        }
    }
}
