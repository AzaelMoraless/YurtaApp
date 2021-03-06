package mx.com.azaelmorales.yurtaapp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import mx.com.azaelmorales.yurtaapp.utilerias.Servidor;

public class PedidosViewFragment extends Fragment {

    private ListView listView;
    private SearchView searchView;
    private ArrayList<Pedido> listaPedidos;
    private  AdapterPedido adaptador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos_view, container, false);
        listView = (ListView)view.findViewById(R.id.listViewPedidos);
        searchView = (SearchView)view.findViewById(R.id.search_pedido);
        cargarDatos();

        return view;
    }


    private void cargarDatos(){ //carga los datos de la base datos en un json

        String url = "http://dissymmetrical-diox.xyz/mostrarPedidos.php";

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
                Toast.makeText(getActivity(),"Error al cargar los datos" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 5;
        listaPedidos = new ArrayList<Pedido>();
        Pedido pedido;
        for (int i=0; i<longitud; i+=columnas) {

            pedido = new Pedido(jsonArray.getString(i),jsonArray.getString(i+1),
                    jsonArray.getString(i+2),jsonArray.getString(i+3),
                    jsonArray.getString(i+4));

            if(pedido.getEstado().equals("0"))
                pedido.setEstado("Sin confirmar");
            else if(pedido.getEstado().equals("1"))
                pedido.setEstado("Confirmado");
            listaPedidos.add(pedido);
        }
        adaptador = new AdapterPedido(getContext(),listaPedidos);
        listView.setAdapter(adaptador);
    }

}