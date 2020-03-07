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
import org.lapoderosa.app.adapter.MovimientoAdapter;
import org.lapoderosa.app.model.Movimiento;
import org.lapoderosa.app.model.Report;
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMovimientosActivity extends MasterClass {
    private RecyclerView rvMovimiento;
    private MovimientoAdapter adaptador;
    private EditText etBuscador;
    private List<Movimiento> listaMovimientos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movimientos);
        progressDialog = new ProgressDialog(this);
        listaMovimientos = new ArrayList<>();

        etBuscador = findViewById(R.id.etBuscarM);
        rvMovimiento = findViewById(R.id.rvMovimiento);

        etBuscador.addTextChangedListener(new TextWatcher() {
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

        rvMovimiento.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.HOST) + getResources().getString(R.string.URL_MOVIMIENTO));

        adaptador = new MovimientoAdapter(AdminMovimientosActivity.this, listaMovimientos);
        rvMovimiento.setAdapter(adaptador);
    }

    public void filtrar(String texto) {
        ArrayList<Movimiento> filtrarLista = new ArrayList<>();
        for (Movimiento movimiento : listaMovimientos) {
            if (movimiento.getUsuario().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(movimiento);
            }
            if (movimiento.getUsuarioInteraccion().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(movimiento);
            }
            if (movimiento.getFecha().contains(texto)) {
                filtrarLista.add(movimiento);
            }
        }
        adaptador.filtrar(filtrarLista);
    }

    @Override
    protected Map<String, String> putParams() {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("usu_usuario", SharedPrefManager.getInstance(this).getKeyUsuario());
        return parametros;
    }

    @Override
    protected void inicializarStringVariables() {
    }

    @Override
    protected void responseConexion(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Movimientos");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                listaMovimientos.add(
                        new Movimiento(
                                jsonObject1.getString("usu_usuario"),
                                jsonObject1.getString("usu_que_hizo"),
                                jsonObject1.getString("usu_fecha"),
                                jsonObject1.getString("usu_hora"),
                                jsonObject1.getString("usu_usuario_interaccion")
                        )
                );
            }

            adaptador = new MovimientoAdapter(AdminMovimientosActivity.this, listaMovimientos);
            rvMovimiento.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
