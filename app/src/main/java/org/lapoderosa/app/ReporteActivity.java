package org.lapoderosa.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lapoderosa.app.R;

import java.util.Calendar;

public class ReporteActivity extends AppCompatActivity {

    Button btnGuardar;
    private static final String TAG = "ReporteActivity";

    private TextView tvDate1,tvDate2,tvHora1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        tvDate1 = findViewById(R.id.tvDateEntrevista);
        tvDate2 = findViewById(R.id.tvDateHecho);

        tvDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                tvDate1.setText(date);
            }
        };

        tvDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                tvDate2.setText(date);
            }
        };

        /*tvHora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time time = new Time();
            }
        });*/
    }
}
