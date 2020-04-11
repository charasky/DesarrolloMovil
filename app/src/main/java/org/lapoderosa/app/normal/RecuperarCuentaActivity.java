package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityRecuperarcuentaBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.JavaMailAPI;
import org.lapoderosa.app.util.MyAnimation;

import java.util.HashMap;
import java.util.Map;

public class RecuperarCuentaActivity extends MasterClass {
    private String email;
    private ActivityRecuperarcuentaBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarcuentaBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        progressDialog = new ProgressDialog(this);

        binding.rBtSiguiente.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            recuperar();
        });

        binding.layoutRecuperarCuenta.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    private void recuperar() {
        inicializarVariables();
        if (check(email, binding.rEmail, "Ingrese email")) {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_ACCOUNT_RECOVERY));
        } else {
            makeTxt("Ingrese email", RecuperarCuentaActivity.this);
        }
    }

    public Boolean check(String string, TextInputLayout txt, String mensaje) {
        if (string.isEmpty()) {
            txt.setError(mensaje);
            return false;
        }
        return true;
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_usuario", email);
        return parametros;
    }

    @Override
    protected void inicializarVariables() {
        email = binding.rEmail.getEditText().getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        String mensaje = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            makeTxt(jsonObject.getString("message"), RecuperarCuentaActivity.this);
            mensaje = jsonObject.getString("existe");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mensaje.equals("0")) {
            binding.rEmail.setError("Este email no existe");
        } else {
            enviarEmail(email);
            startActivity(new Intent(RecuperarCuentaActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void enviarEmail(String email) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email);
        javaMailAPI.execute();
    }
}
