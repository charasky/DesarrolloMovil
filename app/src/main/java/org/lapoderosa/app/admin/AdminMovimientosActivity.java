package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

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
import org.lapoderosa.app.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminMovimientosActivity extends MasterClass {
    private RecyclerView rvMovimiento;
    private MovimientoAdapter adaptador;
    private List<Movimiento> listaMovimientos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movimientos);
        progressDialog = new ProgressDialog(this);
        listaMovimientos = new ArrayList<>();

        rvMovimiento = findViewById(R.id.rvMovimiento);

        rvMovimiento.setLayoutManager(new GridLayoutManager(this, 1));

        ejecutarServicio(getResources().getString(R.string.URL_MOVIMIENTO));

        adaptador = new MovimientoAdapter(AdminMovimientosActivity.this, listaMovimientos);
        rvMovimiento.setAdapter(adaptador);
    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {
        parametros.put("usu_usuario", SharedPrefManager.getInstance(this).getKeyUsuario());
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
