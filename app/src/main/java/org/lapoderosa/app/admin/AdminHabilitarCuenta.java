package org.lapoderosa.app.admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lapoderosa.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.VolleySingleton;
import org.lapoderosa.app.util.SharedPrefManager;
import org.lapoderosa.app.adapter.UserAdapter;
import org.lapoderosa.app.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminHabilitarCuenta extends MasterClass {
    private RecyclerView rvHabilitar;
    private UserAdapter adaptador;
    private List<User> listaUsuarios;
    private Button button1, button2;
    private String user, hora, fecha;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateHourFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_habilitar_usuarios);
        progressDialog = new ProgressDialog(this);
        listaUsuarios = new ArrayList<>();

        button1 = findViewById(R.id.huAbutton1);
        button2 = findViewById(R.id.huAbutton2);
        rvHabilitar = findViewById(R.id.rvHabilitar);
        rvHabilitar.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.URL_USUARIOS_TO_ENABLED));

        adaptador = new UserAdapter(AdminHabilitarCuenta.this, listaUsuarios);
        rvHabilitar.setAdapter(adaptador);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarCuentas();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void habilitarCuentas() {
        inicializarStringVariables();
        respuestaQueUsuariosAprobarOrEliminar(getResources().getString(R.string.URL_ENVIAR_RESPUESTA), listaUsuarios);
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
            rvHabilitar.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_asamblea", SharedPrefManager.getInstance(this).getKeyAsamblea());
        parametros.put("usu_administrador", SharedPrefManager.getInstance(this).getKeyTypeUser());
    }

    @Override
    protected void inicializarStringVariables() {
        fecha = dateFormat.format(new Date());
        hora = dateHourFormat.format(new Date());
        user = SharedPrefManager.getInstance(this).getKeyUsuario();
    }

    private void respuestaQueUsuariosAprobarOrEliminar(String URL, final List<User> listaUsuarios) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
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
                //Log.d("Clicked", "usuario: " + user);
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
