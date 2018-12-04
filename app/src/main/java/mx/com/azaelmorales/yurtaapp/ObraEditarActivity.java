package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ObraEditarActivity extends AppCompatActivity implements  View.OnClickListener,
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText editTextFolio,editTextNombre,editTextDependencia,editTextLugar,
            editTextFechaInicio;

    private TextInputLayout textInputLayoutNombre,textInputLayoutDependencia,textInputLayoutLugar,
            textInputLayoutFecha;
    private AppCompatSpinner spinnerTipo;
    List<String> listaTipos;
    ArrayAdapter<String> adapterSpinnerTipos;
    private int dia,mes,anio;
    private String id,nombre,dependencia,lugar,fecha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_editar);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_edit_obra);
        setSupportActionBar(toolbar);
         toolbar.setTitle("Editar empleado");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTextFolio = (EditText)findViewById(R.id.et_folio_obra);
        editTextFolio.setKeyListener(null);


        editTextNombre =(EditText)findViewById(R.id.et_nombre_obra);
        editTextDependencia =(EditText)findViewById(R.id.et_dependencia_obra);
        editTextLugar=(EditText)findViewById(R.id.et_lugar_obra);
        editTextFechaInicio =(EditText)findViewById(R.id.et_fecha_obra);

        textInputLayoutNombre =(TextInputLayout)findViewById(R.id.til_nombre_obra);
        textInputLayoutDependencia =(TextInputLayout)findViewById(R.id.til_dependencia_obra);
        textInputLayoutLugar =(TextInputLayout)findViewById(R.id.til_lugar_obra);
        textInputLayoutFecha =(TextInputLayout)findViewById(R.id.til_fecha_obra);

        spinnerTipo = (AppCompatSpinner)findViewById(R.id.spn_tipo_obra);
        listaTipos = new ArrayList<>();
        String[] tipos = {"Pavimentacion","Techado","Agua potable"};
        Collections.addAll(listaTipos, tipos);
        adapterSpinnerTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTipos);
        adapterSpinnerTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterSpinnerTipos);
        spinnerTipo.setSelection(0);

        editTextFechaInicio.setOnClickListener(this);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            editTextNombre.setText(b.getString("NOMBRE"));
            editTextFolio.setText(b.getString("ID"));
            editTextDependencia.setText(b.getString("DEPENDENCIA"));

            editTextLugar.setText( b.getString("LUGAR"));
            editTextFechaInicio.setText(b.getString("FECHAINI"));

            String tipo = b.getString("TIPO");


            if(tipo.equals("Pavimentacion"))
                spinnerTipo.setSelection(0);
            else if(tipo.equals("Techado"))
                spinnerTipo.setSelection(1);
            else
                spinnerTipo.setSelection(2);

        }

    }

    @Override
    public void onClick(View view) {
        if(view==editTextFechaInicio){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int i, int i1, int i2) {
                    fecha =  i +"-"+(i1+1)+"-"+i2;
                    editTextFechaInicio.setText(fecha);
                }
            },anio,mes,dia);
            datePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modificar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_aceptar:
                inicializarValores();
                actualizarObra();
                Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
                finish();

               /* iniciarValores();
                if(true){//validaEntradas()
                    rq = Volley.newRequestQueue(this);
                    actualizarEmpleado();//hace un update a la base de datos
                    //limpiarCampos();
                    Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(this,"Algunos campos son incorrectos" ,Toast.LENGTH_LONG).show();
                }*/
                return true;
            case R.id.nav_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Error al actualizar" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
    }


    private void actualizarObra(){
        RequestQueue rq;
        JsonRequest jrq;
        rq = Volley.newRequestQueue(this);
        String url ="http://dissymmetrical-diox.xyz/modificarObra.php?id=" +id +
                "&nombre="+nombre+"&dependencia="+ dependencia+
                "&lugar="+lugar+"&fecha="+fecha+
                "&tipo="+ spinnerTipo.getSelectedItem().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    public void inicializarValores(){
        id = editTextFolio.getText().toString().trim();
        nombre = editTextNombre.getText().toString().trim();
        dependencia = editTextDependencia.getText().toString().trim();
        lugar = editTextLugar.getText().toString().trim();
    }
}
