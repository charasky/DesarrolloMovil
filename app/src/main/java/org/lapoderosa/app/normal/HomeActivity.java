package org.lapoderosa.app.normal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lapoderosa.app.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button ingresar = findViewById(R.id.homeIngresar);
        TextView reporteAnonimo = findViewById(R.id.homeReporteAnonimo);
        TextView contacto = findViewById(R.id.homeContacto);
        TextView registrarse = findViewById(R.id.homeRegistrarse);

        ingresar.setOnClickListener(view -> {
            AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
            animation.setDuration(500);
            ingresar.setAlpha(1f);
            ingresar.startAnimation(animation);

            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        });

        reporteAnonimo.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ReporteAnonimo.class)));

        contacto.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ContactoActivity.class)));

        registrarse.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, RegistrarseActivity.class)));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
