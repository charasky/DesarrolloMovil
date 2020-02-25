package org.lapoderosa.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_ASAMBLEA = "usu_asamble";
    private static final String KEY_USUARIO = "usu_usuario";
    private static final String KEY_NAME = "usu_nombres";
    private static final String KEY_SURNAME = "usu_apellidos";
    private static final String KEY_ENABLED_USER = "usu_validacion";
    private static final String KEY_TYPE_USER = "usu_administrador";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(String usu_asamblea, String usu_usuario,String usu_nombres,String usu_apellidos, String usu_validacion, String usu_administrador) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ASAMBLEA, usu_asamblea);
        editor.putString(KEY_USUARIO, usu_usuario);
        editor.putString(KEY_NAME, usu_nombres);
        editor.putString(KEY_SURNAME, usu_apellidos);
        editor.putString(KEY_ENABLED_USER, usu_validacion);
        editor.putString(KEY_TYPE_USER, usu_administrador);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USUARIO, null) != null) {
            return true;
        }
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyUsuario() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USUARIO, null);
    }

    public String getKeyName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getKeySurname() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURNAME, null);
    }

    //get de si el usuario esta habilitado
    public String getKeyEnabledUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ENABLED_USER, null);
    }

    //get de si el usuario es administrador
    public String getKeyTypeUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TYPE_USER, null);
    }

    public String getKeyAsamblea() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ASAMBLEA, null);
    }
}