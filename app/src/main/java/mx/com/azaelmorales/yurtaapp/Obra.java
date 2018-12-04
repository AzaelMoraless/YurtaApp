package mx.com.azaelmorales.yurtaapp;

public class Obra {
    private String id;
    private String nombre;
    private String dependencia;
    private String lugar;
    private String fecha;
    private String tipo;

    public Obra(String id, String nombre, String dependencia, String lugar, String fecha, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.dependencia = dependencia;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
    }
    public Obra(String id){
        this.id =id;
    }
    public Obra(String id,String nombre,String lugar){
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependecnia) {
        this.dependencia = dependecnia;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
