package mx.com.azaelmorales.yurtaapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DetallePedidoActivity extends AppCompatActivity {
    private TextView textViewFolio,textViewFecha,textViewEstado;
    private ListView listView;
    private Button button;
    private ArrayList<Material> listMaterial;
    private  AdapterMaterialSolicitado adaptador;
    private String folio,idObra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_pedidos);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pedidos");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewFolio = (TextView)findViewById(R.id.textViewFolioP);
        textViewFecha =(TextView)findViewById(R.id.textViewFechaP);
        textViewEstado =(TextView)findViewById(R.id.textViewEstadoP);
        listView =(ListView)findViewById(R.id.listViewMaterialSol);
        button = (Button)findViewById(R.id.buttonConfirmarPedido);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            folio =b.getString("FOLIO");
            textViewFolio.setText("Folio: "+folio);
            textViewFecha.setText("Fecha: "+b.getString("FECHA"));
            textViewEstado.setText("Estado: "+b.getString("ESTADO"));
            idObra= b.getString("ID_OBRA");
            cargarDatos();
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               agregarInventario();
            }
        });

    }

    private void cargarDatos(){
        String url = "http://dissymmetrical-diox.xyz/materialesPedidos.php?folio="+folio;

        RequestQueue requestQueue = Volley.newRequestQueue(DetallePedidoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    cargarListView(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePedidoActivity.this,"Error al cargar los datos" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        int longitud = jsonArray.length();
        int columnas = 4;
        listMaterial = new ArrayList<Material>();
        Material material;
        for (int i=0; i<longitud; i+=columnas) {
            material = new Material(jsonArray.getString(i),jsonArray.getString(i+1),
                   "","",
                    "", jsonArray.getString(i+2),jsonArray.getString(i+3));
            listMaterial.add(material);
        }
        adaptador = new AdapterMaterialSolicitado(DetallePedidoActivity.this,listMaterial);
        listView.setAdapter(adaptador);
    }




    public void agregarInventario(){//registrar el inventario del pedido en la base de datos
        ////iniciarValor();
        String urlPedido = "http://dissymmetrical-diox.xyz/agregarInventario.php?id_obra="+idObra;
        RequestQueue requestQueuePedido = Volley.newRequestQueue(DetallePedidoActivity.this);
        StringRequest stringRequestPedido = new StringRequest(Request.Method.GET, urlPedido, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String codigo,cantidad;
                int existencias;
                for(int i=0; i<listMaterial.size(); i++){
                    existencias = Integer.parseInt(listMaterial.get(i).getExistecias());
                    codigo = listMaterial.get(i).getCodigo();
                    cantidad = listMaterial.get(i).getCantidadSolicitada();
                    existencias = existencias-(Integer.parseInt(cantidad));

                    agregarDetallenInventario(response,codigo,cantidad);
                    actualizarAlmacen(codigo,existencias+"");

                    if(existencias<=3)
                        enviarNotificacion(listMaterial.get(i).getNombre(),existencias+"");
                }
                Toast.makeText(DetallePedidoActivity.this,"Pedido confirmado :)" ,Toast.LENGTH_LONG).show();
                confirmarPedido();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePedidoActivity.this,"Error al registrar el inventario" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueuePedido.add(stringRequestPedido);
    }

    public void agregarDetallenInventario(String idInventario,String codigo,String cantidad){
        String url = "http://dissymmetrical-diox.xyz/agregarDetalleInven.php?id="+idInventario+
                "&codigo="+codigo+"&cantidad="+cantidad+"&folio="+folio;

        RequestQueue requestQueue= Volley.newRequestQueue(DetallePedidoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePedidoActivity.this,"Error al registrar el detalle_inven" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    public void actualizarAlmacen(String codigo,String existencias){//confirma el pedido y actualiza el almacen
        String url = "http://dissymmetrical-diox.xyz/actualizarMaterialExistencias.php?codigo="+codigo+
                "&existencias="+existencias;
        RequestQueue requestQueue = Volley.newRequestQueue(DetallePedidoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePedidoActivity.this,"Error al actualizar" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    public void confirmarPedido(){
        //http://dissymmetrical-diox.xyz/actualizarPedido.php?folio=2&fecha=2018-12-09&estado=1
        String url = "http://dissymmetrical-diox.xyz/actualizarPedido.php?folio="+folio+"&estado=1";
        RequestQueue requestQueue = Volley.newRequestQueue(DetallePedidoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePedidoActivity.this,"Error al actualizar" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    public void enviarNotificacion(String material,String cantidad){
        NotificationCompat.Builder nBuilder;
        NotificationManager nNotiMgr =(NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        ///Intent intent= new Intent(MainActivity.this, ResulAtivity.class);
        ///PendingIntent resulPedint = PendingIntent.getActivity(MainActivity.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder= new NotificationCompat.Builder(getApplicationContext())
                ///.setContentIntent(resulPedint)
                .setSmallIcon(icono)
                .setContentTitle("YutaApp")
                .setContentText("Quedan: " + cantidad + " de " + material)
                .setVibrate(new long[]{100,250,100,500})
                .setAutoCancel(true);

        nNotiMgr.notify(1,nBuilder.build());
    }
}
