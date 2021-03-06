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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MaterialEditarFragment extends Fragment implements  Response.Listener<JSONObject>,Response.ErrorListener{

    private ListView listView;
    private String [][] datos ;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public MaterialEditarFragment() {

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
        View view = inflater.inflate(R.layout.fragment_material_editar, container, false);
        listView = (ListView)view.findViewById(R.id.listViewMaterial);
        request= Volley.newRequestQueue(getContext());
        cargarDatos();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),MaterialEditarActivity.class);
                intent.putExtra("CODIGO",datos[i][0]);
                intent.putExtra("NOMBRE",datos[i][1]);
                intent.putExtra("MARCA",datos[i][3]);
                intent.putExtra("TIPO",datos[i][2]);
                startActivity(intent);
            }
        });

        return view;

    }
    private void cargarDatos(){ //carga los datos de la base datos en un json
        //http://localhost/login/mostrar_empleados.php
        String url ="http://dissymmetrical-diox.xyz/listar.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    } //

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast toast1 =
                Toast.makeText(getContext(),
                        "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

        toast1.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray json = response.getJSONArray("material");
            cargarListView(json);
        }
        catch (Exception e){}

    }

    private void cargarListView(JSONArray jsonArray) throws JSONException {
        //pasa el json devuelto por el query a una matriz de String

        int longitud = jsonArray.length();
        int columnas = 6;
        int n = (longitud)/columnas;
        datos = new String[longitud][columnas-1];
        int j =0;
        for (int i=0; i<longitud; i++) {
            JSONObject jsonObject=jsonArray.getJSONObject(i);

            datos[j][0] = jsonObject.optString("codigoh"); //codigo
            datos[j][1] = jsonObject.optString("nombre"); //nombre
            datos[j][2] = jsonObject.optString("tipo"); //tipo
            datos[j][3] = jsonObject.optString("marca");//marca
            datos[j][4] = jsonObject.optString("estado_m");//estado


            //lista.add(jsonArray.getString(i+5) + " " + jsonArray.getString(i) + " " +jsonArray.getString(i+6));
            j++;
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(new Adaptador_mat(getContext(),datos));
    }


}
