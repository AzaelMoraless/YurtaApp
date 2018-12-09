package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class ObraAgregarMaterialActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewMaterial;
    private SearchView searchView;
    private TextView textViewNombre,textViewExistencias,textViewMarca;
    private Button buttonAdd,buttonSub;
    private ArrayList<Material> listaMaterial;
    private  AdapterMaterial adaptador;
    private TextView textViewID,textViewLugar;
    private String tipoObra;
    private ArrayList<Pedido> arrayListPedidos;
    private EditText editTextCantidad;
    static  ArrayList<Material> arrayListMateriales;
    private int cantidad=0;
    ArrayList<Material> listaMaterialesPedido;
    Material material;
    private boolean buscar;
    private Button buttonAgregarMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        arrayListPedidos = new ArrayList<Pedido>();
        listaMaterialesPedido = new ArrayList<Material>();


        listViewMaterial =(ListView)findViewById(R.id.listViewMaterialAdd);
        searchView =(SearchView)findViewById(R.id.search_material);
        textViewNombre = (TextView)findViewById(R.id.textViewNombreMat);
        textViewMarca = (TextView)findViewById(R.id.textViewMarcaMat);
        textViewExistencias =(TextView)findViewById(R.id.textViewExisMat);
        buttonAdd =(Button)findViewById(R.id.buttonAdd);
        buttonSub = (Button)findViewById(R.id.buttonSub);
        buttonAgregarMaterial = (Button)findViewById(R.id.buttonAgregarMaterial);
        editTextCantidad =(EditText)findViewById(R.id.editTextCantidadMat);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonAgregarMaterial.setOnClickListener(this);

        cargarDatos();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){

        }

        listViewMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(buscar){
                    textViewNombre.setText("Nombre: " + AdapterMaterial.listaFiltrada.get(i).getNombre());
                    textViewMarca.setText("Marca: "+ AdapterMaterial.listaFiltrada.get(i).getMarca());
                    textViewExistencias.setText("Existencias: " + AdapterMaterial.listaFiltrada.get(i).getExistecias());
                    buscar = false;
                }else{
                    textViewNombre.setText("Nombre: " + listaMaterial.get(i).getNombre());
                    textViewMarca.setText("Marca: "+ listaMaterial.get(i).getMarca());
                    textViewExistencias.setText("Existencias: " + listaMaterial.get(i).getExistecias());
                }
                Material materialAux = buscarMaterial(listaMaterial.get(i).getCodigo());
                if(materialAux!=null){
                    cantidad = Integer.parseInt(materialAux.getCantidadSolicitada());
                    material = materialAux;
                }else{
                    cantidad =0;
                    material = listaMaterial.get(i);
                }
                editTextCantidad.setText(""+cantidad);
            }
        });
   }

   private void cargarDatos(){
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
                            buscar =true;
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
                    jsonArray.getString(i+4),jsonArray.getString(i+5),"0");
            listaMaterial.add(material);
        }
        adaptador = new AdapterMaterial(ObraAgregarMaterialActivity.this,listaMaterial);
        listViewMaterial.setAdapter(adaptador);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar_material, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_aceptar:
                ObraAgregarFragment.arrayListMateriales = listaMaterialesPedido;
                AdapterMaterialSolicitado adaptador;
                adaptador = new AdapterMaterialSolicitado(ObrasActivity.getAppContext(), listaMaterialesPedido);
                ObraAgregarFragment.listViewMaterialesPedidos.setAdapter(adaptador);
                finish();
                return true;
            case R.id.nav_cancelar:

                finish();
                return true;
            case R.id.nav_ver_lista:
                for(int i=0; i<listaMaterialesPedido.size(); i++)
                    Toast.makeText(this,"Material Pedido: " + listaMaterialesPedido.get(i).getNombre()
                                    + "Cantidad solicitada: " +listaMaterialesPedido.get(i).getCantidadSolicitada()
                            ,Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        if(view==buttonSub){
            cantidad--;
            if(cantidad<=0)
                cantidad =0;
            editTextCantidad.setText(""+cantidad);

        }else if(view==buttonAdd){
            cantidad++;
            if(cantidad>Integer.parseInt(material.getExistecias())){
                cantidad = Integer.parseInt(material.getExistecias());
            }
            editTextCantidad.setText(""+cantidad);
        }else if(view ==buttonAgregarMaterial){

            if(!actualizarMaterialP(material.getCodigo(),cantidad+"")){
                material.setCantidadSolicitada(""+cantidad);
                listaMaterialesPedido.add(material);
            }
        }
    }


    public Material buscarMaterial(String codigo){
        for(int i=0; i<listaMaterialesPedido.size(); i++)
            if(listaMaterialesPedido.get(i).getCodigo().equals(codigo))
                return listaMaterialesPedido.get(i);
        return null;
    }

    public boolean actualizarMaterialP(String codigo,String cantidad){
        for(int i=0; i<listaMaterialesPedido.size(); i++){
            if(listaMaterialesPedido.get(i).getCodigo().equals(codigo)){
                listaMaterialesPedido.get(i).setCantidadSolicitada(cantidad);
                return true;
            }
        }
        return false;
    }
}
