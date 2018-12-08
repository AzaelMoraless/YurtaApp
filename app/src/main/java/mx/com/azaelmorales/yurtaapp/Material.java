package mx.com.azaelmorales.yurtaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Material implements Parcelable {
    private String codigo;
    private String nombre;
    private String tipo;
    private String marca;
    private String estado;
    private String existecias;
    private String cantidadSolicitada;

    public String getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(String cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Material(String codigo, String nombre, String tipo, String marca, String estado, String existecias, String
            cantidadSolicitada) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.estado = estado;
        this.existecias = existecias;
        this.cantidadSolicitada = cantidadSolicitada;


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

    protected Material(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        tipo = in.readString();
        marca = in.readString();
        estado = in.readString();
        existecias = in.readString();
        cantidadSolicitada = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(nombre);
        dest.writeString(tipo);
        dest.writeString(marca);
        dest.writeString(estado);
        dest.writeString(existecias);
        dest.writeString(cantidadSolicitada);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Material> CREATOR = new Parcelable.Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };
}