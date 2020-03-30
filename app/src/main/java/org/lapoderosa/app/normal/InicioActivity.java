package org.lapoderosa.app.normal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.lapoderosa.app.R;

import org.lapoderosa.app.util.SharedPrefManager;

public class InicioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button btBusqueda = findViewById(R.id.btBusqueda);
        Button btReporte = findViewById(R.id.btReporte);
        Button btCerrar = findViewById(R.id.btCerrarSesion);

        btBusqueda.setOnClickListener(view -> {
            startActivity(new Intent(InicioActivity.this, BusquedarReporteActivity.class));
            animation(btBusqueda);
        });

        btReporte.setOnClickListener(view -> {
            startActivity(new Intent(InicioActivity.this, ReporteActivity.class));
            animation(btReporte);
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

