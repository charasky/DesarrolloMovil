package org.lapoderosa.app.normal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;
import com.lapoderosa.app.databinding.ActivityHomeBinding;

import org.lapoderosa.app.util.MyAnimation;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.btIngresar.setOnClickListener(view -> {
            MyAnimation.blink(view, this);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.tvReporteAnonimo.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ReporteAnonimo.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.tvContacto.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ContactoActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.tvRegistrarse.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, RegistrarseActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
