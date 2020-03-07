package org.lapoderosa.app.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Check {
    private List<Boolean> listaParaCheckear = new LinkedList<>();

    public Boolean isStringEmpty(String string, EditText txt, String mensaje) {
        if (string.isEmpty()) {
            txt.setError(mensaje);
            return false;
        }
        return true;
    }

    public Boolean isStringEmpty(String string, TextView txt, String mensaje, Context context) {
        if (string.equals("dd/mm/yyyy") || string.equals("HH:mm") || string.isEmpty()) {
            txt.setTextColor(Color.parseColor("#FF0000"));
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Boolean isStringEmpty(String string, TextInputLayout txt, String mensaje) {
        if (string.isEmpty()) {
            txt.setError(mensaje);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Boolean> finalValidation() {
        return this.variablesParaCheckiar().stream()
                .filter(v -> v.equals(false))
                .collect(Collectors.toList());
    }

    private List<Boolean> variablesParaCheckiar() {
        return this.listaParaCheckear;
    }

    public void addListToCheck(Boolean condicion){
        this.listaParaCheckear.add(condicion);
    }
}
