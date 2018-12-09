package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
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
    private ArrayList<Material> arrayListMaterial;
    private  AdapterMaterialSolicitado adapter;
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
        listView =(ListView)findViewById(R.id.listViewPedidosEspe);
        button = (Button)findViewById(R.id.buttonConfirmarPedido);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            textViewFolio.setText("Folio: "+b.getString("FOLIO"));
            textViewFecha.setText("Fecha: "+b.getString("FECHA"));
            textViewEstado.setText("Estado: "+b.getString("ESTADO"));
        }

        cargarDatos();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //al confirmar pedido actualiza los materiales existentes
                int existencias,cantidad;
                for(int i=0; i<arrayListMaterial.size(); i++){
                    existencias = Integer.parseInt(arrayListMaterial.get(i).getExistecias());
                    cantidad = Integer.parseInt(arrayListMaterial.get(i).getCantidadSolicitada());
                    existencias = existencias -cantidad;
                    //actualiza el almacen para los materiales solcitados
                    actualizarAlmacen(arrayListMaterial.get(i).getCodigo(),existencias+"");
                }
            }
        });

    }

    private void cargarDatos(){ //carga los datos de la base datos en un json
        //mostrar los datos devueltos por la funcion
            //materiales
        String url = "http://dissymmetrical-diox.xyz/mostrarPedidos.php";

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
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 3;
        arrayListMaterial = new ArrayList<Material>();
        Material material;
        for (int i=0; i<longitud; i+=columnas) {

            material = new Material(jsonArray.getString(i),jsonArray.getString(i+1),
                    jsonArray.getString(i+2));

        }
        adapter = new AdapterMaterialSolicitado(DetallePedidoActivity.this,arrayListMaterial);
        listView.setAdapter(adapter);
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

}
