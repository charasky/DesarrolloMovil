package org.lapoderosa.app.normal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lapoderosa.app.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button ingresar = findViewById(R.id.homeIngresar);
        final TextView reporteAnonimo = findViewById(R.id.homeReporteAnonimo);
        TextView contacto = findViewById(R.id.homeContacto);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                //finish();
            }
        });

        reporteAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HomeActivity.this, ));
            }
        });

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent());
            }
        });
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
