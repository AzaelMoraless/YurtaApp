package mx.com.azaelmorales.yurtaapp.utilerias;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Validar {

    public static boolean nombre(String nombre, TextInputLayout textInputLayout) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            textInputLayout.setError("Nombre inválido");
            return false;
        } else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public static boolean telefono(String telefono,TextInputLayout textInputLayout) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
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

    public  static boolean rfc(String rfc){
        Pattern patron = Pattern.compile("/^([A-ZÑ&]{3,4}) ?(?:- ?)?(\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])) ?(?:- ?)?([A-Z\\d]{2})([A\\d])$/");
        return patron.matcher(rfc).matches() || rfc.length()>13;
    }
}
