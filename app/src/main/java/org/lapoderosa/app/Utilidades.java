package org.lapoderosa.app;

import android.widget.EditText;
import android.widget.RadioButton;

public class Utilidades {

    public String parentesco(RadioButton victima, RadioButton familiar, EditText otro){
        if(victima.isChecked()){
            return victima.getText().toString();
        }else if(familiar.isChecked()){
            return familiar.getText().toString();
        }else{
            return otro.getText().toString();
        }
    }
}
