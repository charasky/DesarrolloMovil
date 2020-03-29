package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.Check;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends MasterClass {
    private Check check = new Check();
    private Integer cont = 1;
    private String user = "";
    private TextInputLayout txlUsuario, txlPassword;
    private TextView etOlvidastesContraseña;
    private Button btnLogin;
    private String usuario, password;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);

        layout = findViewById(R.id.layoutLogin);
        txlUsuario = findViewById(R.id.etUserName);
        txlPassword = findViewById(R.id.dmPassword1);
        btnLogin = findViewById(R.id.btLogin);
        etOlvidastesContraseña = findViewById(R.id.etOlvidastesContraseña);

        etOlvidastesContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecuperarCuentaActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
                animation.setDuration(500);
                btnLogin.setAlpha(1f);
                btnLogin.startAnimation(animation);
                usuarioLogin();
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void usuarioLogin() {
        inicializarStringVariables();
        if (checkVariables().isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_LOGIN));
        } else {
            Toast.makeText(LoginActivity.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Boolean> checkVariables() {
        check.addListToCheck(check.isStringEmpty(usuario, txlUsuario, "Ingrese email"));
        check.addListToCheck(check.isStringEmpty(password, txlPassword, "Ingrese contraseña"));
        return check.finalValidation();
    }


    @Override
    protected void inicializarStringVariables() {
        usuario = txlUsuario.getEditText().getText().toString().trim();
        password = txlPassword.getEditText().getText().toString().trim();
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if (!obj.getBoolean("error")) {
                SharedPrefManager.getInstance(getApplicationContext())
                        .userLogin(
                                obj.getString("usu_asamblea"),
                                obj.getString("usu_usuario"),
                                obj.getString("usu_nombres"),
                                obj.getString("usu_apellidos"),
                                obj.getString("usu_validacion"),
                                obj.getString("usu_administrador")
                        );
                this.chooseInicio(Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyTypeUser()), Boolean.parseBoolean(SharedPrefManager.getInstance(this).getKeyEnabledUser()));
            } else {
                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                this.metodoUsuarioContador();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void chooseInicio(Boolean admin, Boolean habilitado) {
        if (!habilitado && !admin) {
            Toast.makeText(getApplicationContext(), "El usuario aun no tiene acceso por Administradores", Toast.LENGTH_LONG).show();
        }

        if (!habilitado && admin) {
            Toast.makeText(getApplicationContext(), "El usuario aun no tiene acceso", Toast.LENGTH_LONG).show();
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
