package mx.com.azaelmorales.yurtaapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.List;

import mx.com.azaelmorales.yurtaapp.utilerias.Servidor;


public class EmpleadosViewFragment extends Fragment  {
    private ListView listView;
    private SearchView searchView;
    private ArrayList<Empleado> listaEmpleados;
    private  Adaptador adaptador;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empleados_view, container, false);
        listView = (ListView)view.findViewById(R.id.listViewEmpleados);

        cargarDatos();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent =new Intent(view.getContext(),EmpleadoDetalleActivity.class);

                    intent.putExtra("RFC",listaEmpleados.get(i).getRfc());
                    intent.putExtra("PUESTO",listaEmpleados.get(i).getPuesto());
                    intent.putExtra("SEXO",listaEmpleados.get(i).getSexo());
                    intent.putExtra("TELEFONO",listaEmpleados.get(i).getTelefono());
                    intent.putExtra("FECHAN",listaEmpleados.get(i).getFechaNacimiento());
                    intent.putExtra("NOMBRE",listaEmpleados.get(i).getNombre());
                    intent.putExtra("CORREO",listaEmpleados.get(i).getCorreo());
                    intent.putExtra("PASSWORD",listaEmpleados.get(i).getPassword());
                    startActivity(intent);
                }
            });


            searchView = (SearchView) view.findViewById(R.id.search_empleado);


        return view;
    }

    private void cargarDatos(){ //carga los datos de la base datos en un json

        String url = Servidor.URL+Servidor.MOSTRAR_EMPLEADOS;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                Toast.makeText(getActivity(),"Erro al cargar los datos" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 10;
        listaEmpleados = new ArrayList<Empleado>();
        Empleado empleado;
            for (int i=0; i<longitud; i+=columnas) {
                empleado = new Empleado(jsonArray.getString(i),jsonArray.getString(i+1),
                        jsonArray.getString(i+2),jsonArray.getString(i+3),
                        jsonArray.getString(i+4),jsonArray.getString(i+5),
                        jsonArray.getString(i+6),jsonArray.getString(i+8),
                        jsonArray.getString(i+7));
                listaEmpleados.add(empleado);
            }
        adaptador = new Adaptador(getContext(),listaEmpleados);
        listView.setAdapter(adaptador);
    }


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_busqueda, menu); // TU MENU


        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_buscar: // TU OPCION
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
