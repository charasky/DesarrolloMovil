package org.lapoderosa.app.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

public class AdminRestablecerContraseñaActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminrestablecercuenta);
    }


    /*
    private boolean validate() {
        boolean valid = true;
        //todo PASSWORD
        if (!(password1.matches(".{8,20}") || password2.matches(".{8,20}"))) {
            dmPassword.setError("la contraseña debe ser mayor a 8 digitos");
            dmPassword2.setError("la contraseña debe ser mayor a 8 digitos");
            valid = false;
        }
        //TODO CONTRASEÑA CARACTER ESPECIAL
        if (!(password1.matches(".*[!@#$%^&*+=?-].*") || password2.matches(".*[!@#$%^&*+=?-].*"))) {
            dmPassword.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            dmPassword2.setError("la contraseña debe contener un caracter especial: !@#$%^&*+=?-");
            valid = false;
        }
        //TODO CONTRASEÑA DEBE TENER ALMENOS 1 NUMERO
        if (!(password1.matches(".*\\d.*") || password2.matches(".*\\d.*"))) {
            dmPassword.setError("la contraseña debe contener almenos un numero");
            dmPassword2.setError("la contraseña debe contener almenos un numero");
            valid = false;
        }
        //TODO DEBE TENER UNA LETRA MINUSCULA
        if (!(password1.matches(".*[a-z].*") || password2.matches(".*[a-z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra minuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra minuscula");
            valid = false;
        }
        //TODO DEBE TENER UNA LETRA MAYUSCULA
        if (!(password1.matches(".*[A-Z].*") || password2.matches(".*[A-Z].*"))) {
            dmPassword.setError("Contraseña debe contener almenos una letra mayuscula");
            dmPassword2.setError("Contraseña debe contener almenos una letra mayuscula");
            valid = false;
        }
        //TODO NO DEBE TENER ESPACIOS
        if (password1.matches(".*\\s.*") || password2.matches(".*\\s.*")) {
            dmPassword.setError("Contraseña no debe contener espacios");
            dmPassword2.setError("Contraseña no debe contener espacios");
            valid = false;
        }
        if (password1.isEmpty()) {
            dmPassword.setError("Ingrese contraseña");
            valid = false;
        }
        if (password2.isEmpty()) {
            dmPassword2.setError("Reingrese contraseña");
            valid = false;
        }
        if (!password1.equals(password2)) {
            dmPassword.setError("Contraseñas no coinciden");
            //dmPassword2.setError("Contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }*/

}
