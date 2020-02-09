package org.lapoderosa.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.lapoderosa.app.normal.InicioActivity;
import org.lapoderosa.app.normal.LoginActivity;
import org.lapoderosa.app.admin.AdminInicioActivity;
import org.lapoderosa.app.util.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public abstract class MasterClass extends AppCompatActivity {

    //TODO RECORDAR PONER EL progressDialog = new ProgressDialog(this); EN LA ACTIVITY QUE USE MASTERCLASS
    public ProgressDialog progressDialog;

    protected void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                responseConexion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                putParams(parametros);
                return parametros;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected void irLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    protected void irInicioAdmin() {
        startActivity(new Intent(getApplicationContext(), AdminInicioActivity.class));
        finish();
    }

    protected void irInicio() {
        startActivity(new Intent(getApplicationContext(), InicioActivity.class));
        finish();
    }

    protected abstract void putParams(Map<String, String> parametros) throws AuthFailureError;

    protected abstract void inicializarStringVariables();

    protected abstract void responseConexion(String response);
}