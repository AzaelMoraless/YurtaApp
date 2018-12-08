package mx.com.azaelmorales.yurtaapp;

public class Pedido {
    private String folioPedido;
    private String fecha;
    private String idObra;
    private String estado;

    public Pedido(String folioPedido, String fecha, String idObra, String estado) {
        this.folioPedido = folioPedido;
        this.fecha = fecha;
        this.idObra = idObra;
        this.estado = estado;
    }

    public String getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(String folioPedido) {
        this.folioPedido = folioPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdObra() {
        return idObra;
    }

    public void setIdObra(String idObra) {
        this.idObra = idObra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
