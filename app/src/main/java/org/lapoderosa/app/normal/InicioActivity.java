package org.lapoderosa.app.normal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.databinding.ActivityInicioBinding;

import org.lapoderosa.app.util.MyAnimation;
import org.lapoderosa.app.util.SharedPrefManager;

public class InicioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInicioBinding binding = ActivityInicioBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.btnBusqueda.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(InicioActivity.this, BusquedarReporteActivity.class));
        });

        binding.btnReporte.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            startActivity(new Intent(InicioActivity.this, ReporteActivity.class));
        });

        binding.btnCerrarSesion.setOnClickListener(view -> {
            MyAnimation.blink(view,this);
            cerrarSesion();
        });
    }

    private void cerrarSesion() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(InicioActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Si", (dialog, which) -> InicioActivity.super.onBackPressed()).setNegativeButton("No", (dialog, i) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

