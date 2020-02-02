package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.MasterClass;
import org.lapoderosa.app.util.SharedPrefManager;
import org.lapoderosa.app.adapter.UserAdapter;
import org.lapoderosa.app.model.User;

import java.util.ArrayList;
import java.util.Map;

public class AdminHabilitarCuenta extends MasterClass {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userArrayList;
    private ArrayAdapter<User> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_busqueda_validar);

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        userArrayList = new ArrayList<>();

        ejecutarServicio(getResources().getString(R.string.URL_USUARIOS_TO_ENABLED));

        userAdapter = new UserAdapter(AdminHabilitarCuenta.this, userArrayList);
        recyclerView.setAdapter(userAdapter);
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
                                //todo falta agregar al php que devuelva el id :v
                                //jsonObject1.getInt("id"),
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
}
