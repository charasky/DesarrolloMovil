package org.lapoderosa.app.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

import org.lapoderosa.app.normal.BusquedarReporteActivity;
import org.lapoderosa.app.normal.HomeActivity;
import org.lapoderosa.app.normal.LoginActivity;
import org.lapoderosa.app.normal.ReporteActivity;
import org.lapoderosa.app.util.SharedPrefManager;

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
                startActivity(new Intent(AdminInicioActivity.this, BusquedarReporteActivity.class));
                animation(btBusqueda);
            }
        });

        btReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminInicioActivity.this, ReporteActivity.class));
                animation(btReporte);
            }
        });

        btHabilitarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminInicioActivity.this, AdminHabilitarCuenta.class));
                animation(btHabilitarCuenta);
            }
        });

        btRestablecerContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminInicioActivity.this,AdminRestablecerContraseñaActivity.class));
                animation(btRestablecerContraseña);
            }
        });

        btMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminInicioActivity.this,AdminMovimientosActivity.class));
                animation(btMovimientos);
            }
        });

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
                animation(btCerrar);
            }
        });
    }

    private void animation(Button button){
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(500);
        button.setAlpha(1f);
        button.startAnimation(animation);
    }

    private void cerrarSesion() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(AdminInicioActivity.this, HomeActivity.class));
        finish();
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
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
