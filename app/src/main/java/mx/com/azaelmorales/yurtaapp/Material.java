package mx.com.azaelmorales.yurtaapp;

public class Material {
    private String codigo;
    private String nombre;
    private String tipo;
    private String marca;
    private String estado;
    private String existecias;

    public Material(String codigo, String nombre, String tipo, String marca, String estado, String existecias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.estado = estado;
        this.existecias = existecias;
    }

    public Material(String nombre,String marca,String existencias){
        this.nombre = nombre;
        this.marca = marca;
        this.existecias = existencias;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getExistecias() {
        return existecias;
    }

    public void setExistecias(String existecias) {
        this.existecias = existecias;
    }
}
