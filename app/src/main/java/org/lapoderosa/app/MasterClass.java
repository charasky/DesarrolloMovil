package org.lapoderosa.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.lapoderosa.app.util.VolleySingleton;

import java.util.Map;

public abstract class MasterClass extends AppCompatActivity {

    //TODO RECORDAR PONER EL progressDialog = new ProgressDialog(this); EN LA ACTIVITY QUE USE MASTERCLASS
    public ProgressDialog progressDialog;

    protected void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            progressDialog.dismiss();
            responseConexion(response);
        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return putParams();
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected abstract Map<String, String> putParams();

    protected abstract void inicializarStringVariables();

    protected abstract void responseConexion(String response);

    protected void makeTxt(String mensaje , Context context) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }
}