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

        btBusqueda.setOnClickListener(view -> {
            startActivity(new Intent(AdminInicioActivity.this, BusquedarReporteActivity.class));
            animation(btBusqueda);
        });

        btReporte.setOnClickListener(view -> {
            startActivity(new Intent(AdminInicioActivity.this, ReporteActivity.class));
            animation(btReporte);
        });

        btHabilitarCuenta.setOnClickListener(view -> {
            startActivity(new Intent(AdminInicioActivity.this, AdminHabilitarCuenta.class));
            animation(btHabilitarCuenta);
        });

        btRestablecerContraseña.setOnClickListener(view -> {
            startActivity(new Intent(AdminInicioActivity.this,AdminRestablecerContraseñaActivity.class));
            animation(btRestablecerContraseña);
        });

        btMovimientos.setOnClickListener(view -> {
            startActivity(new Intent(AdminInicioActivity.this,AdminMovimientosActivity.class));
            animation(btMovimientos);
        });

        btCerrar.setOnClickListener(view -> {
            cerrarSesion();
            animation(btCerrar);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Si", (dialog, which) -> AdminInicioActivity.super.onBackPressed()).setNegativeButton("No", (dialog, i) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
