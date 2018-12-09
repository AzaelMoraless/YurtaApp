package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pedido {
    private String folioPedido;
    private String fecha;
    private String fechaConfirmacion;
    private String idObra;
    private String estado;

    public String getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(String fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public Pedido(String folioPedido, String fecha, String fecha2,String estado,String idObra) {
        this.folioPedido = folioPedido;
        this.fecha = fecha;
        this.fechaConfirmacion = fecha2;
        this.idObra = idObra;
        this.estado = estado;
    }

    public Pedido(String folio,String fecha,String estado){
        this.folioPedido=folio;
        this.fecha = fecha;
        this.estado = estado;
    }
    public Pedido(){}
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
