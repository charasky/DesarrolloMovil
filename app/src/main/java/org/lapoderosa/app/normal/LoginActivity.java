package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.Check;
import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends MasterClass {
    private Integer cont = 1;
    private String user = "";
    private String usuario, password;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.etOlvidastesContrasenia.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RecuperarCuentaActivity.class)));

        binding.btLogin.setOnClickListener(view -> {
            MyAnimation.blink(view, LoginActivity.this);
            usuarioLogin();
        });

        binding.layoutLogin.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void usuarioLogin() {
        inicializarVariables();
        if (checkVariables().isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_LOGIN));
        } else {
            makeTxt("Ingrese usuario y contraseña", LoginActivity.this);
        }
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("contador", cont.toString());
        parametros.put("usu_usuario", usuario);
        parametros.put("usu_password", password);
        return parametros;
    }

    private List<Boolean> checkVariables() {
        Check check = new Check();
        check.addListToCheck(check.isStringEmpty(usuario, binding.etUserName, "Ingrese email"));
        check.addListToCheck(check.isStringEmpty(password, binding.dmPassword1, "Ingrese contraseña"));
        return check.finalValidation();
    }

    @Override
    protected void inicializarVariables() {
        usuario = Objects.requireNonNull(binding.etUserName.getEditText()).getText().toString().trim();
        password = Objects.requireNonNull(binding.dmPassword1.getEditText()).getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getBoolean("error")) {
                SharedPrefManager.getInstance(getApplicationContext())
                        .userLogin(
                                jsonObject.getString("usu_asamblea"),
                                jsonObject.getString("usu_usuario"),
                                jsonObject.getString("usu_nombres"),
                                jsonObject.getString("usu_apellidos"),
                                jsonObject.getString("usu_validacion"),
                                jsonObject.getString("usu_administrador")
                        );
                this.chooseInicio(Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser()), Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser()));
            } else {
                makeTxt(jsonObject.getString("message"), LoginActivity.this);
                this.metodoUsuarioContador();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void chooseInicio(Boolean admin, Boolean habilitado) {
        if (!habilitado && !admin) {
            makeTxt("El usuario aun no tiene acceso por Administradores", LoginActivity.this);
        }

        if (!habilitado && admin) {
            makeTxt("El usuario aun no tiene acceso", LoginActivity.this);
        }

        if (admin && habilitado) {
            startActivity(new Intent(LoginActivity.this, AdminInicioActivity.class));
            finish();
        }

        if (!admin && habilitado) {
            startActivity(new Intent(LoginActivity.this, InicioActivity.class));
            finish();
        }
    }

    private void metodoUsuarioContador() {
        if (!user.equals(usuario)) {
            user = usuario;
            cont = 2;
        } else {
            cont++;
        }
    }
}
