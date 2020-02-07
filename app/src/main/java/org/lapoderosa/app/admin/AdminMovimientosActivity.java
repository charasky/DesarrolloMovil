package org.lapoderosa.app.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.lapoderosa.app.R;

import org.lapoderosa.app.MasterClass;

import java.util.Map;

public class AdminMovimientosActivity extends MasterClass {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movimientos);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    protected void putParams(Map<String, String> parametros) throws AuthFailureError {

    }

    @Override
    protected void inicializarStringVariables() {

    }

    @Override
    protected void responseConexion(String response) {

    }
}
