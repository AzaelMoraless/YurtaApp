package mx.com.azaelmorales.yurtaapp.utilerias;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;

import java.util.regex.Pattern;
//Clase para validar datos de entrada
public class Validar {

    public  static boolean rfc(String rfc,TextInputLayout textInputLayout){
        //Pattern patron = Pattern.compile("([A-Z,Ñ,&]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$");
        Pattern patron = Pattern.compile("([A-Z,Ñ,&]{3,4}([1-9][0-9]|(0[0]))(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$");
        if(!patron.matcher(rfc).matches() || rfc.length()>13){
            textInputLayout.setError("RFC inválido");
            return false;
        }
        else
            textInputLayout.setError(null);
        return true;
    }

    public static boolean nombre(String nombre, TextInputLayout textInputLayout) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            textInputLayout.setError("Entrada inválida");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }
// implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    public static boolean telefono(String telefono,TextInputLayout textInputLayout) {

        if (!Patterns.PHONE.matcher(telefono).matches() || telefono.length()>12) {
            textInputLayout.setError("Teléfono inválido");
            return false;
        } else {
            textInputLayout.setError(null);
        }

        return true;
    }

    public static  boolean correo(String correo,TextInputLayout textInputLayout) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            textInputLayout.setError("Correo electrónico inválido");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }
    public static  boolean correo(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            return false;
        }
        return true;
    }
    public static  boolean fecha_nac(String fecha_n,TextInputLayout textInputLayout) {
        Pattern patron = Pattern.compile("^\\d{4}([\\-/.])(0?[1-9]|1[1-2])\\1(3[01]|[12][0-9]|0?[1-9])$");
        if (!patron.matcher(fecha_n).matches() ) {
            textInputLayout.setError("Fecha inválida");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public static  boolean passwords(String password1,String password2,TextInputLayout textInputLayout) {
        if (!password1.equals(password2)){
            textInputLayout.setError("Las contraseñas no coinciden");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public static boolean password(String password,TextInputLayout textInputLayout){
        Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        if (!patron.matcher(password).matches() ) {
            textInputLayout.setError("Minimo 8 caracteres, al menos un dígito,una minúscula y una mayúscula");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public boolean cantidad(String c,TextInputLayout textInputLayout){
        try{
            int cantidad = Integer.parseInt(c);
            if(cantidad<0)
                return false;
            return true;
        }catch(Exception e){}
        return false;
    }
}
