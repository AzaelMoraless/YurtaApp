package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class ObraModificarFragment extends Fragment {
    private SearchView searchView;
    private ListView listViewObras;
    private ArrayList<Obra> listaObras;
    private  AdaptadorObras adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_obra_modificar, container, false);
        searchView = (SearchView)view.findViewById(R.id.search_obra);
        listViewObras = (ListView) view.findViewById(R.id.listViewObrasModificar);

        cargarDatos();

        //caragar editar obra
        listViewObras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),ObraEditarActivity.class);
                intent.putExtra("ID",listaObras.get(i).getId());
                intent.putExtra("NOMBRE",listaObras.get(i).getNombre());
                intent.putExtra("DEPENDENCIA",listaObras.get(i).getDependencia());
                intent.putExtra("LUGAR",listaObras.get(i).getLugar());
                intent.putExtra("FECHAINI",listaObras.get(i).getFecha());
                intent.putExtra("TIPO",listaObras.get(i).getTipo());
                startActivity(intent);
            }
        });



        return view;
    }



    private void cargarDatos(){ //carga los datos de la base datos en un json

        String url = "http://dissymmetrical-diox.xyz/mostrarObras.php";

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
                Toast toast1 =
                        Toast.makeText(getContext(),
                                "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

                toast1.show();
            }
        });
        requestQueue.add(stringRequest);
    }


    private void cargarListView(JSONArray jsonArray) throws JSONException {
        int longitud = jsonArray.length();
        int columnas = 6; //filas de la tablaa obra
        listaObras = new ArrayList<Obra>();
        Obra obra;
        for (int i=0; i<longitud; i+=columnas) {
            obra = new Obra(jsonArray.getString(i),jsonArray.getString(i+1),
                    jsonArray.getString(i+2),jsonArray.getString(i+3),
                    jsonArray.getString(i+4),jsonArray.getString(i+5));
            listaObras.add(obra);
        }
        adaptador = new AdaptadorObras(getContext(),listaObras);
        listViewObras.setAdapter(adaptador);
    }

}
