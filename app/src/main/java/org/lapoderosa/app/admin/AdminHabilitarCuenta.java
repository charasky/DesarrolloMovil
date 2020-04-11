package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityAdminHabilitarUsuariosBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.adapter.UserAdapter;
import org.lapoderosa.app.model.User;
import org.lapoderosa.app.util.DateDefinido;
import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;
import org.lapoderosa.app.util.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHabilitarCuenta extends MasterClass {
    private ActivityAdminHabilitarUsuariosBinding binding;
    private UserAdapter adaptador;
    private List<User> listaUsuarios;
    private String user, hora, fecha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHabilitarUsuariosBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        progressDialog = new ProgressDialog(this);
        listaUsuarios = new ArrayList<>();

        binding.rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_USUARIOS_TO_ENABLED));

        adaptador = new UserAdapter(AdminHabilitarCuenta.this, listaUsuarios);
        binding.rvLista.setAdapter(adaptador);

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAnimation.blink(view,AdminHabilitarCuenta.this);
                habilitarCuentas();
            }
        });

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAnimation.blink(view,AdminHabilitarCuenta.this);
                onBackPressed();
            }
        });
    }

    private void habilitarCuentas() {
        inicializarVariables();
        respuestaQueUsuariosAprobarOrEliminar(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_ENVIAR_RESPUESTA), listaUsuarios);
        startActivity(new Intent(AdminHabilitarCuenta.this, AdminInicioActivity.class));
        finish();
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Users");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                listaUsuarios.add(
                        new User(
                                jsonObject1.getInt("id"),
                                jsonObject1.getString("usu_usuario"),
                                jsonObject1.getString("usu_nombres"),
                                jsonObject1.getString("usu_apellidos"),
                                jsonObject1.getString("usu_asamblea")
                        )
                );
            }

            adaptador = new UserAdapter(AdminHabilitarCuenta.this, listaUsuarios);
            binding.rvLista.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_asamblea", SharedPrefManager.getInstance(this).getKeyAsamblea());
        parametros.put("usu_administrador", SharedPrefManager.getInstance(this).getKeyTypeUser());
        return parametros;
    }

    @Override
    protected void inicializarVariables() {
        fecha = DateDefinido.getFechaDispositivo();
        hora = DateDefinido.getHoraDispositivo();
        user = SharedPrefManager.getInstance(this).getKeyUsuario();
    }

    private void respuestaQueUsuariosAprobarOrEliminar(String URL, final List<User> listaUsuarios) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                makeTxt(jsonObject.getString("message"),AdminHabilitarCuenta.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> makeTxt(error.toString(),AdminHabilitarCuenta.this)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONObject jsonObjecUsers = new JSONObject();
                for (User user : listaUsuarios) {
                    try {
                        jsonObjecUsers.put(String.valueOf(user.getId()), user.getEnabled());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usu_usuario", user);
                parametros.put("usu_fecha", fecha);
                parametros.put("usu_hora", hora);
                parametros.put("params", jsonObjecUsers.toString());
                return parametros;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
