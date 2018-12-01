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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

public class EmpleadosEditarActivity extends AppCompatActivity implements  View.OnClickListener,
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText editTextRFC,editTextNombre,editTextFechaN
                        ,editTextTelefono,editTextCorreo,editTextPassword;
    private AppCompatSpinner spinnerSexo,spinnerPuesto;
    private TextInputLayout tilNombre,tilRfc,tilCorreo,tilTelefono,tilDate;
    List<String> listaGeneros,listaPuestos;
    ArrayAdapter<String> adapterSpinner,adapterSpinnerPuestos;
    private int dia,mes,anio;
    private String rfc,nombre,fecha_nacimiento,telefono,correo,password;
    private boolean a,b,c,d,e,f,g;
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
        tilDate = (TextInputLayout)findViewById(R.id.til_add_empleado_date);
        tilTelefono = (TextInputLayout)findViewById(R.id.til_add_empleado_tel);
        tilCorreo = (TextInputLayout)findViewById(R.id.til_add_empleado_correo);

        editTextRFC = (EditText)findViewById(R.id.etEmpleadoMoRFC);
        editTextRFC.setKeyListener(null);
        editTextNombre = (EditText)findViewById(R.id.etEmpleadoMoNombre);

        editTextFechaN = (EditText)findViewById(R.id.etEmpleadoMoFecha);
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

        editTextFechaN.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            editTextPassword.setText(b.getString("PASSWORD"));
            editTextRFC.setText(b.getString("RFC"));
            editTextNombre.setText(b.getString("NOMBRE"));
            editTextFechaN.setText(b.getString("FECHAN"));
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

        implemetarTextWatcher();


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
                if(true){//validaEntradas()
                    rq = Volley.newRequestQueue(this);
                    actualizarEmpleado();//hace un update a la base de datos
                    //limpiarCampos();
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
    @Override
    public void onClick(View view) { //boton agregar epleado, cancelar y desplegar el calendario en el campo fecha
        try{
            if(view==editTextFechaN){
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        fecha_nacimiento =  i +"-"+(i1+1)+"-"+i2;
                        editTextFechaN.setText(fecha_nacimiento);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        }catch (Exception e){
            Toast.makeText(this,"Error e date" + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

    }

    public void implemetarTextWatcher(){ //implementa un escuchador para la validacion de los datos en las cajas de texto
        editTextRFC.addTextChangedListener(new TextWatcher() {
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

        editTextNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                e = Validar.telefono(String.valueOf(s),tilTelefono);
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
                f = Validar.correo(String.valueOf(s),tilCorreo);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextFechaN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                g = Validar.fecha_nac(String.valueOf(s),tilDate);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public boolean validaEntradas(){
        if(a&&b&&e&&f&&g)
            return true;
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Error al actualizar" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Registro actualizado",Toast.LENGTH_LONG).show();
    }

    private void actualizarEmpleado(){
        //http://dissymmetrical-diox.xyz/modificarEmpleado.php?rfc=MORA800825569&nombre=Esteban Azael Morales Vega&f_nacimiento=1997-01-25&telefono=951256848&correo=azaelmorales029@homtail.com&passw=2329&puesto=jefe&sexo=Masculino
        String url ="http://dissymmetrical-diox.xyz/modificarEmpleado.php?rfc=" +rfc +
                "&nombre="+nombre+"&f_nacimiento="+ fecha_nacimiento+
                "&telefono="+telefono+"&correo="+correo+"&passw="+password +
                "&puesto=" + spinnerPuesto.getSelectedItem().toString()+
                "&sexo="+ spinnerSexo.getSelectedItem().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    public void iniciarValores(){ //incializa los valores
        rfc = editTextRFC.getText().toString();
        nombre = editTextNombre.getText().toString();
        telefono = editTextTelefono.getText().toString();
        correo = editTextCorreo.getText().toString();
        password = editTextPassword.getText().toString();
    }


    public void limpiarCampos(){//setea los campos
        editTextRFC.setText("");
        editTextNombre.setText("");
        editTextTelefono.setText("");
        editTextFechaN.setText("");
        editTextPassword.setText("");
        editTextPassword.setText("");
        spinnerSexo.setSelection(0);
        spinnerPuesto.setSelection(0);
    }

}

