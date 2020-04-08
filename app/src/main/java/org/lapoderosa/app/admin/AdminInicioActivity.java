package org.lapoderosa.app.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.databinding.ActivityAdminInicioBinding;

import org.lapoderosa.app.normal.BusquedarReporteActivity;
import org.lapoderosa.app.normal.HomeActivity;
import org.lapoderosa.app.normal.ReporteActivity;
import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;

public class AdminInicioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdminInicioBinding binding = ActivityAdminInicioBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.btnBusqueda.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, BusquedarReporteActivity.class));
        });

        binding.btnReporte.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, ReporteActivity.class));
        });

        binding.btnHabilitar.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this, AdminHabilitarCuenta.class));
        });

        binding.btnRestablecer.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this,AdminRestablecerContraseñaActivity.class));
        });

        binding.btnMovimientos.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(AdminInicioActivity.this,AdminMovimientosActivity.class));
        });

        binding.btnCerrarSesion.setOnClickListener(view -> {
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
