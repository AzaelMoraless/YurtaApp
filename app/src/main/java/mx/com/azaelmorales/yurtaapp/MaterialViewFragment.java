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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MaterialViewFragment extends Fragment implements  Response.Listener<JSONObject>,Response.ErrorListener{
    private ListView listView;
    private String [][] datos ;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public MaterialViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_view, container, false);
        listView = (ListView)view.findViewById(R.id.listViewMaterial);
        request= Volley.newRequestQueue(getContext());
        cargarDatos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),MaterialDetalleActivity.class);
                intent.putExtra("CODIGO",datos[i][0]);
                intent.putExtra("NOMBRE",datos[i][1]);
                intent.putExtra("TIPO",datos[i][2]);
                intent.putExtra("CANTIDAD",datos[i][3]);
                intent.putExtra("MARCA",datos[i][4]);
                intent.putExtra("PRECIO",datos[i][5]);
                intent.putExtra("ESTADO",datos[i][6]);
                startActivity(intent);
            }
        });
        return view;
    }
    private void cargarDatos(){  String url ="http://dissymmetrical-diox.xyz/listar.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

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
      int longitud = jsonArray.length();
        int columnas = 8;
        int n = (longitud)/columnas;
        datos = new String[longitud][columnas-1];
            int j =0;
            for (int i=0; i<longitud; i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                datos[j][0] = jsonObject.optString("codigoh"); //codigo
                datos[j][1] = jsonObject.optString("nombre"); //nombre
                datos[j][2] = jsonObject.optString("tipo"); //tipo
                datos[j][3] = jsonObject.optString("cantidad");//cantidad
                datos[j][4] = jsonObject.optString("marca");//marca
                datos[j][5] = jsonObject.optString("precio");//precio
                datos[j][6] = jsonObject.optString("estado_m");//estado
                j++;
            }
             listView.setAdapter(new Adaptador_mat(getContext(),datos));
    }


}
