package mx.com.azaelmorales.yurtaapp;

public class Pedido {
    private String folioPedido;
    private String responsable;
    private String estado;

    public Pedido(String folioPedido, String responsable, String estado) {
        this.folioPedido = folioPedido;
        this.responsable = responsable;
        this.estado = estado;
    }

    public String getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(String folioPedido) {
        this.folioPedido = folioPedido;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
