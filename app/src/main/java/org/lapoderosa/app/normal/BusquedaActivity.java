package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.SharedPrefManager;
import org.lapoderosa.app.user.AdaptadorUsuarios;
import org.lapoderosa.app.user.Reporte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusquedaActivity extends MasterClass {

    private EditText etBuscador;
    private RecyclerView rvLista;
    private AdaptadorUsuarios adaptador;
    private List<Reporte> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        progressDialog = new ProgressDialog(this);
        etBuscador = findViewById(R.id.etBuscar);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        listaUsuarios = new ArrayList<>();

        ejecutarServicio(getResources().getString(R.string.URL_USUARIOS));

        adaptador = new AdaptadorUsuarios(BusquedaActivity.this, listaUsuarios);
        rvLista.setAdapter(adaptador);
    }

    public void filtrar(String texto) {
        ArrayList<Reporte> filtrarLista = new ArrayList<>();
            //TODO con los if pueda ir filtrando tmbien por otro tipo fecha
        for(Reporte usuario : listaUsuarios) {
            if(usuario.getFullName().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
            if(usuario.getCiudad().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
            if(usuario.getFecha().contains(texto)){
                filtrarLista.add(usuario);
            }
        }

        adaptador.filtrar(filtrarLista);
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Reportes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                listaUsuarios.add(
                        new Reporte(
                                jsonObject1.getString("id"),
                                jsonObject1.getString("r_pais"),
                                jsonObject1.getString("r_ciudad"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("vic_nombre"),
                                jsonObject1.getString("vic_apellido")
                        )
                );
            }

            adaptador = new AdaptadorUsuarios(BusquedaActivity.this, listaUsuarios);
            rvLista.setAdapter(adaptador);

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
}
