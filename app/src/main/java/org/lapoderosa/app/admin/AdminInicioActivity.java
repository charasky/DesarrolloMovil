package org.lapoderosa.app.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

import org.lapoderosa.app.normal.BusquedaActivity;
import org.lapoderosa.app.normal.InicioActivity;
import org.lapoderosa.app.normal.LoginActivity;
import org.lapoderosa.app.normal.ReporteActivity;

public class AdminInicioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inicio);

        Button btBusqueda = findViewById(R.id.btAdminBusqueda);
        Button btReporte = findViewById(R.id.btAdminReporte);
        Button btHabilitarCuenta = findViewById(R.id.btAdminHabilitar);
        Button btRestablecerContraseña = findViewById(R.id.btAdminRestablecer);
        Button btMovimientos = findViewById(R.id.btAdminMovimientos);
        Button btCerrar = findViewById(R.id.btAdminCerrarSesion);

        btBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInicioActivity.this, BusquedaActivity.class);
                startActivity(intent);
            }
        });

        btReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInicioActivity.this, ReporteActivity.class);
                startActivity(intent);
            }
        });

        btHabilitarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminInicioActivity.this, AdminHabilitarCuenta.class);
                startActivity(intent);
            }
        });

        btRestablecerContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminInicioActivity.this,AdminRestablecerContraseñaActivity.class);
                startActivity(intent);
            }
        });

        btMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminInicioActivity.this,AdminMovimientosActivity.class);
                startActivity(intent);
            }
        });

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //si da click si sale de la app
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminInicioActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
