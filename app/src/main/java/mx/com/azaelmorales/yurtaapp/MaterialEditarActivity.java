package mx.com.azaelmorales.yurtaapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
public class MaterialEditarActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{
    Button btnaceptar,btncancelar;
    EditText textViewNombre,textViewFN,textViewTel;
    String cod="",nomb=" ",tipo=" ",marca=" ";
    RequestQueue rq;
    JsonRequest jrq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_material);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Editar empleado");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView textViewRFC = (TextView)findViewById(R.id.tVDetEmRFC);
        textViewNombre = (EditText) findViewById(R.id.tVDetEmNombre);
        textViewFN  = (EditText) findViewById(R.id.tVdDetEmFecha);
        textViewTel  = (EditText) findViewById(R.id.tVdDetEmTel);
        btnaceptar=(Button)findViewById(R.id.btn_add_material_aceptar);
        btncancelar=(Button)findViewById(R.id.btn_add_material_cancelar);

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargardatos();
                actualizar();
            }
        });
        Intent intent = getIntent();

        Bundle b = intent.getExtras();
        if(b!=null){
            cod= b.getString("CODIGO");
            textViewRFC.setText("codigo:" + b.getString("CODIGO"));
            textViewNombre.setText(b.getString("NOMBRE"));
            textViewFN.setText(b.getString("MARCA"));
            textViewTel.setText(b.getString("TIPO"));
        }





        rq = Volley.newRequestQueue(this);
    }
    private void cargardatos(){
        nomb=textViewNombre.getText().toString();
        marca=textViewFN.getText().toString();
        tipo=textViewTel.getText().toString();
    }
    private void actualizar(){ //se ejecuta la solicitud al web service alojado en el servidor
        //http://dissymmetrical-diox.xyz/registrom.php?nombre=ppp&tipo=ttt&marca=mmm&estado_m=1
        String url ="http://dissymmetrical-diox.xyz/actualizarm.php?codigoh="+cod+"&nombre="+nomb+"&tipo="+tipo+
                "&marca="+marca+"&estado_m=1";
        url=url.replace(" ","%");

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast toast1 =
                Toast.makeText(this,
                        "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

        toast1.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast toast1 =
                Toast.makeText(this,
                        "datos actualizados", Toast.LENGTH_LONG);

        toast1.show();

    }
}



