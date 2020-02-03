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
import org.lapoderosa.app.util.RequestHandler;
import org.lapoderosa.app.util.SharedPrefManager;

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
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected void volverLogin() {
        if(SharedPrefManager.getInstance(this).getKeyTypeUser().equals("TRUE")){
            startActivity(new Intent(getApplicationContext(), AdminInicioActivity.class));
            finish();
        }else if(SharedPrefManager.getInstance(this).getKeyEnabledUser().equals("TRUE")) {
            startActivity(new Intent(getApplicationContext(), InicioActivity.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    protected void irLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    protected void irInicioAdmin(){
        startActivity(new Intent(getApplicationContext(), AdminInicioActivity.class));
        finish();
    }

    protected void irInicio(){
        startActivity(new Intent(getApplicationContext(), InicioActivity.class));
        finish();
    }

    protected void chooseInicio(String admin, String habilitado){
        //String admin = SharedPrefManager.getInstance(this).getKeyTypeUser();
        //String habilitado = SharedPrefManager.getInstance(this).getKeyEnabledUser();
        if (admin.equals("TRUE") && habilitado.equals("TRUE")) {
            this.irInicioAdmin();
        }
        if (habilitado.equals("TRUE") && admin.equals("FALSE")) {
            this.irInicio();
        }
    }

    protected abstract void putParams(Map<String, String> parametros) throws AuthFailureError;

    protected abstract void inicializarStringVariables();

    protected abstract void responseConexion(String response);
}