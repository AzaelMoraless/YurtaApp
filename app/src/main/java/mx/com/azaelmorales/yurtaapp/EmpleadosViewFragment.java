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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EmpleadosViewFragment extends Fragment    {

    private ListView listView;
    private String [][] datos ;
    public EmpleadosViewFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empleados_view, container, false);
        listView = (ListView)view.findViewById(R.id.listViewEmpleados);
        cargarDatos();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),DetalleEmpleadoActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
    private void cargarDatos(){
        //http://localhost/login/mostrar_empleados.php
        String url ="http://10.0.0.7/login/consultarEmpleados.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast1 =
                        Toast.makeText(getContext(),
                                "Entra", Toast.LENGTH_SHORT);

                toast1.show();
                response = response.replace("][",",");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    cargarListView(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();

                    toast1 =
                            Toast.makeText(getContext(),
                                    "Error" + e.getMessage(), Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 =
                        Toast.makeText(getContext(),
                                "Error"+ error.getMessage(), Toast.LENGTH_LONG);

                toast1.show();
            }
        });
        requestQueue.add(stringRequest);
    } //

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        ArrayList <String> lista = new ArrayList<>();
        int longitud = jsonArray.length();
        int n = (longitud)/8;
        datos = new String[n][3];
            int j =0;
            for (int i=0; i<longitud; i+=8) {
                datos[j][0] = jsonArray.getString(i+5);
                datos[j][1] = jsonArray.getString(i);
                datos[j][2] = jsonArray.getString(i+6);
                lista.add(jsonArray.getString(i+5) + " " + jsonArray.getString(i) + " " +jsonArray.getString(i+6));
                j++;
            }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(new Adaptador(getContext(),datos));
    }


}
