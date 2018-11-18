package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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


public class EmpleadosViewFragment extends Fragment{
    private ListView listView;
    private String [][] datos ;
    public EmpleadosViewFragment() {

    }
    /*datos[j][0] = jsonArray.getString(i); //rfc
                datos[j][1] = //puesto
                datos[j][2] =//sexo
                datos[j][3] = //telefono
                datos[j][4] = //fecha de nacimiento
                datos[j][5] = //nombre
                datos[j][6] = //correo
    * */

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
                intent.putExtra("RFC",datos[i][0]);
                intent.putExtra("PUESTO",datos[i][1]);
                intent.putExtra("SEXO",datos[i][2]);
                intent.putExtra("TELEFONO",datos[i][3]);
                intent.putExtra("FECHAN",datos[i][4]);
                intent.putExtra("NOMBRE",datos[i][5]);
                intent.putExtra("CORREO",datos[i][6]);
                startActivity(intent);
            }
        });
        return view;

    }
    private void cargarDatos(){ //carga los datos de la base datos en un json
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
        //pasa el json devuelto por el query a una matriz de String
        int longitud = jsonArray.length();
        int columnas = 8;
        int n = (longitud)/columnas;
        datos = new String[n][columnas-1];
            int j =0;
            for (int i=0; i<longitud; i+=columnas) {
                datos[j][0] = jsonArray.getString(i); //rfc
                datos[j][1] = jsonArray.getString(i+1); //puesto
                datos[j][2] = jsonArray.getString(i+2); //sexo
                datos[j][3] = jsonArray.getString(i+3);//telefono
                datos[j][4] = jsonArray.getString(i+4);//fecha de nacimiento
                datos[j][5] = jsonArray.getString(i+5);//nombre
                datos[j][6] = jsonArray.getString(i+6);//correo
                //lista.add(jsonArray.getString(i+5) + " " + jsonArray.getString(i) + " " +jsonArray.getString(i+6));
                j++;
            }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(new Adaptador(getContext(),datos));
    }


}
