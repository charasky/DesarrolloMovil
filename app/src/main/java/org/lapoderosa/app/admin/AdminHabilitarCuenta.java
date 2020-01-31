package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.SharedPrefManager;
import org.lapoderosa.app.user.AdaptadorUsuariosValidar;
import org.lapoderosa.app.user.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminHabilitarCuenta extends MasterClass {
    private EditText buscarValidar;
    private RecyclerView rvListaValidar;
    private AdaptadorUsuariosValidar adaptadorValidar;
    private List<Usuario> listaUsuariosValidar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_busqueda_validar);

        progressDialog = new ProgressDialog(this);
        buscarValidar = findViewById(R.id.buscarValidar);
        buscarValidar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrarValidar(s.toString());
            }
        });

        rvListaValidar = findViewById(R.id.rvListaValidar);
        rvListaValidar.setLayoutManager(new GridLayoutManager(this, 1));

        listaUsuariosValidar = new ArrayList<>();

        ejecutarServicio(getResources().getString(R.string.URL_USUARIOS_TO_ENABLED));

        adaptadorValidar = new AdaptadorUsuariosValidar(AdminHabilitarCuenta.this, listaUsuariosValidar);
        rvListaValidar.setAdapter(adaptadorValidar);
    }

    public void filtrarValidar(String texto) {
        ArrayList<Usuario> filtrarListaValidar = new ArrayList<>();

        for(Usuario user : listaUsuariosValidar) {
            if(user.getUsuario().toLowerCase().contains(texto.toLowerCase())) {
                filtrarListaValidar.add(user);
            }
            if(user.getAsamblea().toLowerCase().contains(texto.toLowerCase())) {
                filtrarListaValidar.add(user);
            }
        }

        adaptadorValidar.filtrar(filtrarListaValidar);
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Reportes");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                listaUsuariosValidar.add(
                        new Usuario(
                                jsonObject1.getString("usu_usuario"),
                                jsonObject1.getString("usu_nombres"),
                                jsonObject1.getString("usu_apellidos"),
                                jsonObject1.getString("usu_asamblea")
                        )
                );
            }

            adaptadorValidar = new AdaptadorUsuariosValidar(AdminHabilitarCuenta.this, listaUsuariosValidar);
            rvListaValidar.setAdapter(adaptadorValidar);

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
