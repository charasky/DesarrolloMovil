package org.lapoderosa.app.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

import org.lapoderosa.app.normal.BusquedarReporteActivity;
import org.lapoderosa.app.normal.HomeActivity;
import org.lapoderosa.app.normal.ReporteActivity;
import org.lapoderosa.app.util.MyAnimation;
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

        btBusqueda.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, BusquedarReporteActivity.class));
        });

        btReporte.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, ReporteActivity.class));
        });

        btHabilitarCuenta.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, AdminHabilitarCuenta.class));
        });

        btRestablecerContraseña.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this,AdminRestablecerContraseñaActivity.class));
        });

        btMovimientos.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this,AdminMovimientosActivity.class));
        });

        btCerrar.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            cerrarSesion();
        });
    }

    private void cerrarSesion() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(AdminInicioActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Si", (dialog, which) -> AdminInicioActivity.super.onBackPressed()).setNegativeButton("No", (dialog, i) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
