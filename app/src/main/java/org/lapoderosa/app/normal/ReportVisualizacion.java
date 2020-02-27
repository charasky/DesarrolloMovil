package org.lapoderosa.app.normal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lapoderosa.app.R;

public class ReportVisualizacion extends AppCompatActivity {
    private TextView fullNameVictima, provincia, pais, hora, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_visualizacion);

        fullNameVictima = findViewById(R.id.vName);
        provincia = findViewById(R.id.vProvincia);
        pais = findViewById(R.id.vPais);
        hora = findViewById(R.id.vHora);
        fecha = findViewById(R.id.vFecha);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            fullNameVictima.setText(bundle.getString("name"));
            provincia.setText(bundle.getString("provincia"));
            pais.setText(bundle.getString("pais"));
            hora.setText(bundle.getString("hora"));
            fecha.setText(bundle.getString("fecha"));
        }

    }
}
