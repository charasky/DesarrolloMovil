package org.lapoderosa.app.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDefinido {
    private static Date date = new Date();
    private static SimpleDateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private static DateFormat horaFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static String getFechaDispositivo() {
        return fechaFormat.format(date);
    }

    public static String getHoraDispositivo() {
        return horaFormat.format(date);
    }
}
