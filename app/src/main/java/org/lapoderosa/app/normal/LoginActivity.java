package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.Map;

public class LoginActivity extends MasterClass {
    private Integer cont = 1;
    private String user = "";
    private EditText editUsuario, editPassword;
    private TextView etRegistrarse, etOlvidastesContraseña;
    private Button btnLogin;
    private String usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.etUserName);
        editPassword = findViewById(R.id.dmPassword1);
        etRegistrarse = findViewById(R.id.etRegistrarse);
        btnLogin = findViewById(R.id.btLogin);
        etOlvidastesContraseña = findViewById(R.id.etOlvidastesContraseña);
        progressDialog = new ProgressDialog(this);



        etOlvidastesContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarCuentaActivity.class);
                startActivity(intent);
            }
        });

        etRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioLogin();
            }
        });
    }

    private void usuarioLogin() {
        inicializarStringVariables();
        if (!usuario.isEmpty() && !password.isEmpty()) {
            ejecutarServicio(getResources().getString(R.string.URL_LOGIN));
        } else {
            Toast.makeText(LoginActivity.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("contador", cont.toString());
        parametros.put("usu_usuario", usuario);
        parametros.put("usu_password", password);
    }

    @Override
    protected void inicializarStringVariables() {
        usuario = editUsuario.getText().toString();
        password = editPassword.getText().toString();
    }

    @Override
    protected void responseConexion(String response) {
        String admin = "";
        String habilitado = "";
        try {
            JSONObject obj = new JSONObject(response);
            if (!obj.getBoolean("error")) {
                SharedPrefManager.getInstance(getApplicationContext())
                        .userLogin(
                                obj.getString("usu_asamblea"),
                                obj.getString("usu_usuario"),
                                obj.getString("usu_validacion"),
                                obj.getString("usu_administrador")
                        );
                admin = SharedPrefManager.getInstance(this).getKeyTypeUser();
                habilitado = SharedPrefManager.getInstance(this).getKeyEnabledUser();
            } else {
                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                this.metodoUsuarioContador();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (admin.equals("FALSE") && habilitado.equals("FALSE")) {
            Toast.makeText(getApplicationContext(), "El usuario aun no tiene acceso por Administradores", Toast.LENGTH_LONG).show();
        }

        if(admin.equals("TRUE") && habilitado.equals("FALSE")){
            Toast.makeText(getApplicationContext(), "El usuario aun no tiene acceso", Toast.LENGTH_LONG).show();
        }

        if (admin.equals("TRUE") && habilitado.equals("TRUE")) {
            startActivity(new Intent(getApplicationContext(), AdminInicioActivity.class));
            finish();
        }
        if (habilitado.equals("TRUE") && admin.equals("FALSE")) {
            startActivity(new Intent(getApplicationContext(), InicioActivity.class));
            finish();
        }
    }

    private void metodoUsuarioContador() {
        if (!user.equals(usuario)) {
            user = usuario;
            cont = 2;
        }else{
            cont++;
        }
    }
}
