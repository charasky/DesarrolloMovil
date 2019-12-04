package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(InicioActivity.this, BusquedaActivity.class);
                startActivity(intent);
            }
        });


        btReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(InicioActivity.this, ReporteActivity.class);
                startActivity(intent2);
            }
        });

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent3);
                finish();
            }
        });
    }
}

