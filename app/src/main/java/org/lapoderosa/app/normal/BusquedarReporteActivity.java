package org.lapoderosa.app.normal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.GridLayoutManager;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityBusquedaReporteBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.adapter.ReportAdapter;
import org.lapoderosa.app.model.Report;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BusquedarReporteActivity extends MasterClass {
    private ActivityBusquedaReporteBinding binding;
    private ReportAdapter adaptador;
    private List<Report> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusquedaReporteBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        listaUsuarios = new ArrayList<>();

        binding.etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        binding.rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_USUARIOS));

        adaptador = new ReportAdapter(BusquedarReporteActivity.this, listaUsuarios);
        binding.rvLista.setAdapter(adaptador);

        binding.layoutBusqueda.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void filtrar(String texto) {
        ArrayList<Report> filtrarLista = new ArrayList<>();
        //con los if pueda ir filtrando tmbien por otro tipo fecha
        for (Report usuario : listaUsuarios) {
            if (usuario.getFullName().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
            if (usuario.getProvincia().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
            if (usuario.getFecha().contains(texto)) {
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
                        new Report(
                                jsonObject1.getString("id"),
                                jsonObject1.getString("r_pais"),
                                jsonObject1.getString("r_ciudad"),
                                jsonObject1.getString("fecha"),
                                jsonObject1.getString("vic_nombre"),
                                jsonObject1.getString("vic_apellido"),
                                jsonObject1.getString("r_hora")
                        )
                );
            }

            adaptador = new ReportAdapter(BusquedarReporteActivity.this, listaUsuarios);
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
    }
}
