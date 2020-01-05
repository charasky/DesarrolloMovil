package org.lapoderosa.app.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

public class AdminHabilitarCuenta extends AppCompatActivity {
    private Button hBtSiguiente;
    private EditText hEmail;


    //todo cambiar el tema del layout en la busqueda y ver
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhabilitarcuenta);
        hEmail = findViewById(R.id.hEmail);
        hBtSiguiente = findViewById(R.id.hBtSiguiente);

        hBtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
