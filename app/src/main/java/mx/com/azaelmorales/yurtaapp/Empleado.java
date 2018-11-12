package mx.com.azaelmorales.yurtaapp;

public class Empleado {
    private String correo;
    private String nombre_e;

    public String getNombre() {
        return nombre_e;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre_e= nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
