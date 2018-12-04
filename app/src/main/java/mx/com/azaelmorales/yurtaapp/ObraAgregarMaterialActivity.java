package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import mx.com.azaelmorales.yurtaapp.utilerias.Servidor;

public class ObraAgregarMaterialActivity extends AppCompatActivity {
    private ListView listViewMaterial;
    private SearchView searchView;
    private ArrayList<Material> listaMaterial;
    private  AdapterMaterial adaptador;
    private TextView textViewID,textViewLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_agregar_material);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_materiales);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Materiales");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });


        textViewID = (TextView)findViewById(R.id.textViewIdObra);
        textViewLugar =(TextView)findViewById(R.id.textViewLugarObra);
        listViewMaterial =(ListView)findViewById(R.id.listViewMaterialAdd);
        searchView =(SearchView)findViewById(R.id.search_material);
        cargarDatos();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            textViewID.setText("ID: " + b.getString("ID"));
            textViewLugar.setText("Lugar: "+b.getString("LUGAR"));
        }
    }


    private void cargarDatos(){ //carga los datos de la base datos en un json
        String url = "http://dissymmetrical-diox.xyz/mostrarMaterial.php";
        RequestQueue requestQueue = Volley.newRequestQueue(ObraAgregarMaterialActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    cargarListView(jsonArray);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String query) {
                            adaptador.getFilter().filter(query);
                            return false;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ObraAgregarMaterialActivity.this,"Error al cargar los datos" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 6;
        listaMaterial = new ArrayList<Material>();
        Material material;
        for (int i=0; i<longitud; i+=columnas) {
            material = new Material(jsonArray.getString(i),jsonArray.getString(i+1),
                    jsonArray.getString(i+2),jsonArray.getString(i+3),
                    jsonArray.getString(i+4),jsonArray.getString(i+5));
            listaMaterial.add(material);
        }
        adaptador = new AdapterMaterial(ObraAgregarMaterialActivity.this,listaMaterial);
        listViewMaterial.setAdapter(adaptador);
    }
}
