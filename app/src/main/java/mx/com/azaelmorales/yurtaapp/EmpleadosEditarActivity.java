package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import mx.com.azaelmorales.yurtaapp.utilerias.Validar;
//implements
//        Response.Listener<JSONObject>,Response.ErrorListener
public class EmpleadosEditarActivity extends AppCompatActivity {
    private EditText editTextRFC,editTextNombre
                        ,editTextTelefono,editTextCorreo,editTextPassword;
    private AppCompatSpinner spinnerSexo,spinnerPuesto;
    private TextInputLayout tilNombre,tilRfc,tilCorreo,tilTelefono,tilDate;
    List<String> listaGeneros,listaPuestos;
    ArrayAdapter<String> adapterSpinner,adapterSpinnerPuestos;
    private int dia,mes,anio;
    private String rfc,nombre,fecha_nacimiento,telefono,correo,password;
    private boolean a,b,c,d,e,f,g;
    private  boolean flagb,flagc,flagd;
    RequestQueue rq;
    JsonRequest jrq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados_editar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mod_empleado);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Editar empleado");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tilRfc = (TextInputLayout)findViewById(R.id.til_add_empleado_rfc);
        tilNombre = (TextInputLayout)findViewById(R.id.til_add_empleado_nombre);
       // tilDate = (TextInputLayout)findViewById(R.id.til_add_empleado_date);
        tilTelefono = (TextInputLayout)findViewById(R.id.til_add_empleado_tel);
        tilCorreo = (TextInputLayout)findViewById(R.id.til_add_empleado_correo);

        editTextRFC = (EditText)findViewById(R.id.etEmpleadoMoRFC);
        editTextRFC.setKeyListener(null);
        editTextNombre = (EditText)findViewById(R.id.etEmpleadoMoNombre);


        editTextTelefono = (EditText)findViewById(R.id.etEmpleadoMoTel);
        editTextCorreo = (EditText)findViewById(R.id.etEmpleadoMoCorreo);
        editTextPassword = (EditText)findViewById(R.id.etEmpleadoMoPass);

        spinnerSexo = (AppCompatSpinner)findViewById(R.id.spn_mod_sexo);
        spinnerPuesto = (AppCompatSpinner)findViewById(R.id.spn_mod_puesto);
        listaGeneros = new ArrayList<>();
        listaPuestos = new ArrayList<>();
        String[] generos = {"Masculino","Femenino"};
        String[] puestos = {"Administrador","Jefe de obra"};
        Collections.addAll(listaGeneros, generos);
        Collections.addAll(listaPuestos,puestos);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaGeneros);
        adapterSpinnerPuestos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaPuestos);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerPuestos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapterSpinner);
        spinnerPuesto.setAdapter(adapterSpinnerPuestos);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            editTextPassword.setText(b.getString("PASSWORD"));
            editTextRFC.setText(b.getString("RFC"));
            editTextNombre.setText(b.getString("NOMBRE"));

            editTextTelefono.setText( b.getString("TELEFONO"));
            editTextCorreo.setText(b.getString("CORREO"));

            String puesto = b.getString("PUESTO");
            String sexo = b.getString("SEXO");

            if(puesto.equals("Administrador"))
                spinnerPuesto.setSelection(0);
            else
                spinnerPuesto.setSelection(1);
            if(sexo.equals("Masculino"))
                spinnerSexo.setSelection(0);
            else
                spinnerSexo.setSelection(1);
        }

       /// implemetarTextWatcher();


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
                iniciarValores();
                seModifico();
                if(validaEntradas()){
                    //rq = Volley.newRequestQueue(this);
                    //actualizarEmpleado();//hace un update a la base de datos
                    //limpiarCampos();
                    updateEmpleado();
                    Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(this,"Algunos campos son incorrectos" ,Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.nav_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void implemetarTextWatcher(){ //implementa un escuchador para la validacion de los datos en las cajas de texto
    /*    editTextRFC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a = Validar.rfc(String.valueOf(s),tilRfc);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
        editTextNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flagb =true;
                b = Validar.nombre(String.valueOf(s),tilNombre);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flagc=true;
                c = Validar.telefono(String.valueOf(s),tilTelefono);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flagd=true;
                d = Validar.correo(String.valueOf(s),tilCorreo);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean validaEntradas(){
        if(b&&c&&d)
            return true;
        return false;
    }

    public void seModifico(){
        if(!flagb)
             b=true;
        if(!flagc)
            c=true;
        if(!flagd)
            d=true;
    }
   /* @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Error al actualizar" +error ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
    }
*/
  /*  private void actualizarEmpleado(){
       String url ="http://dissymmetrical-diox.xyz/modificarEmpleado.php?rfc=" +rfc +
                "&nombre="+nombre+"&f_nacimiento="+ fecha_nacimiento+
                "&telefono="+telefono+"&correo="+correo+"&passw="+password +
                "&puesto=" + spinnerPuesto.getSelectedItem().toString()+
                "&sexo="+ spinnerSexo.getSelectedItem().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }*/
    public void iniciarValores(){ //incializa los valores
        rfc = editTextRFC.getText().toString();
        nombre = editTextNombre.getText().toString();
        telefono = editTextTelefono.getText().toString();
        correo = editTextCorreo.getText().toString();
        password = editTextPassword.getText().toString();
    }

    public void updateEmpleado(){
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url  ="http://dissymmetrical-diox.xyz/modificarEmpleado.php?rfc=" +rfc +
                "&nombre="+nombre+"&f_nacimiento="+ fecha_nacimiento+
                "&telefono="+telefono+"&correo="+correo+"&passw="+password +
                "&puesto=" + spinnerPuesto.getSelectedItem().toString()+
                "&sexo="+ spinnerSexo.getSelectedItem().toString();

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);

    }


}

