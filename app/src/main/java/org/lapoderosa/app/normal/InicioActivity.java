package org.lapoderosa.app.normal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RadioButton;

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

        btBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, BusquedarReporteActivity.class));
                animation(btBusqueda);
            }
        });

        btReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, ReporteActivity.class));
                animation(btReporte);
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
        startActivity(new Intent(InicioActivity.this, HomeActivity.class));
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
                        InicioActivity.super.onBackPressed();
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

