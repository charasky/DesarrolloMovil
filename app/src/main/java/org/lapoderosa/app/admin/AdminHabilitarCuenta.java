package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import org.lapoderosa.app.util.RequestHandler;
import org.lapoderosa.app.util.SharedPrefManager;
import org.lapoderosa.app.adapter.UserAdapter;
import org.lapoderosa.app.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminHabilitarCuenta extends MasterClass {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userArrayList;
    private Button button1, button2;
    private RelativeLayout layout;
    private ArrayAdapter<User> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_busqueda_habilitar);
        progressDialog = new ProgressDialog(this);
        userArrayList = new ArrayList<>();

        layout = findViewById(R.id.adBusquedaHabilitar);
        button1 = findViewById(R.id.eAbutton1);
        button2 = findViewById(R.id.eAbutton2);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.URL_USUARIOS_TO_ENABLED));

        userAdapter = new UserAdapter(AdminHabilitarCuenta.this, userArrayList);
        recyclerView.setAdapter(userAdapter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarSelecionRadioButton(getResources().getString(R.string.URL_ENVIAR_RESPUESTA), userArrayList);
                //todo revisar si realmente se podria cambiar el ir inicio dentro del nuevo response
                irInicioAdmin();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irInicioAdmin();
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

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Users");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                userArrayList.add(
                        new User(
                                jsonObject1.getInt("id"),
                                jsonObject1.getString("usu_usuario"),
                                jsonObject1.getString("usu_nombres"),
                                jsonObject1.getString("usu_apellidos"),
                                jsonObject1.getString("usu_asamblea")
                        )
                );
            }
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
    }

    private void enviarSelecionRadioButton(String URL, final ArrayList<User> userArrayList) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                //responseConexion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //envio un object a php
                JSONObject jsonObjecUsers = new JSONObject();
                for (User user : userArrayList) {
                    try {
                        jsonObjecUsers.put(String.valueOf(user.getId()), user.getEnabled());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("Clicked", "onMapa: " + jsonObjecUsers);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("params", jsonObjecUsers.toString());
                return parametros;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
