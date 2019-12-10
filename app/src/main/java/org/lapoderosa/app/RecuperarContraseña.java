package org.lapoderosa.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

public class RecuperarContraseña extends AppCompatActivity {

    Button btSiguiente;
    TextView rLogin;
    EditText recoverEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcuenta);

        btSiguiente = findViewById(R.id.btSiguiente);
        rLogin = findViewById(R.id.rLogin);
        recoverEmail = findViewById(R.id.recoverEmail);


        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecuperarContraseña.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //todo verificar los datos si existen en la base de datos
    }
}
