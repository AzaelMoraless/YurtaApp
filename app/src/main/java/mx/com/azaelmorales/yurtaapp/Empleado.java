package mx.com.azaelmorales.yurtaapp;

public class Empleado {
    private String rfc;
    private String puesto;
    private String sexo;
    private String telefono;

    private String fechaNacimiento;
    private String nombre;
    private String correo;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empleado(String rfc, String puesto, String sexo, String telefono, String fechaNacimiento,
                    String nombre, String correo, String estado) {
        this.rfc = rfc;
        this.puesto = puesto;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.correo = correo;
        this.estado = estado;

    }
    public Empleado(String nombre,String rfc,String correo){
        this.nombre = nombre;
        this.rfc = rfc;
        this.correo = correo;
    }
    public Empleado(){

    }
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
