package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ServiceConfigurationError;


public class EmpleadosModificarFragment extends Fragment {
    private SearchView searchView;
    private ListView listView;
    private ArrayList<Empleado> listaEmpleados;
    private  Adaptador adaptador;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empleados_modificar, container, false);
        try{
            searchView = (SearchView)view.findViewById(R.id.search_empleado);
            listView = (ListView) view.findViewById(R.id.listViewEmpleadosModificar);
        }catch (Exception e){
            Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }


        cargarDatos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),EmpleadosEditarActivity.class);


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


        return view;
    }




    private void cargarDatos(){ //carga los datos de la base datos en un json
        String url = "http://dissymmetrical-diox.xyz/mostrarEmpleados.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                Toast toast1 =
                        Toast.makeText(getContext(),
                                "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

                toast1.show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 9;
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
}
