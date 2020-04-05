package org.lapoderosa.app.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        if (string.equals("dd/mm/yyyy") || string.equals("HH:mm") ) {
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

    public Boolean isStringEmpty(String string, String mensaje, Context context) {
        if (string.isEmpty()) {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    public List<Boolean> finalValidation() {
        List<Boolean> checksFalse = new ArrayList<>();
        for (Boolean checkAnswer: this.variablesParaCheckiar()){
            if (!checkAnswer){
                checksFalse.add(checkAnswer);
            }
        }
        return checksFalse;
    }

    private List<Boolean> variablesParaCheckiar() {
        return this.listaParaCheckear;
    }

    public void addListToCheck(Boolean condicion){
        this.listaParaCheckear.add(condicion);
    }
}
