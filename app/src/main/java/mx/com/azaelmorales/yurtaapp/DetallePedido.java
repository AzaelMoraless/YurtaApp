package mx.com.azaelmorales.yurtaapp;

public class DetallePedido {

    private String folioPedido;
    private String codigoMaterial;
    private String cantidad;

    public DetallePedido(String folioPedido, String codigoMaterial, String cantidad) {
        this.folioPedido = folioPedido;
        this.codigoMaterial = codigoMaterial;
        this.cantidad = cantidad;
    }


    public String getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(String folioPedido) {
        this.folioPedido = folioPedido;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
