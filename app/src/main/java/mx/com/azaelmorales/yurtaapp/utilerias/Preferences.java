package mx.com.azaelmorales.yurtaapp.utilerias;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String STRING_PREFERENCES="mx.com.azaelmorales.yurtaapp";
    public static final String PREFERENCE_ESTADO_SESION = "estado.button.sesion";
    public static final String PREFERENCE_EMPLEADO_NOMBRE = "empleado.nombre";
    public static final String PREFERENCE_EMPLEADO_CORREO = "empleado.correo";

    public static void savePreferenceBoolean(Context context,boolean b,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceString(Context context,String b,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,b).apply();
    }


    public static boolean getPeferenceBoolean(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public static String getPeferenceString(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STRING_PREFERENCES,context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }




}
