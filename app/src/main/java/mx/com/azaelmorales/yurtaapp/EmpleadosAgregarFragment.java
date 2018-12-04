package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import me.anwarshahriar.calligrapher.Calligrapher;
import mx.com.azaelmorales.yurtaapp.utilerias.Validar;


public class EmpleadosAgregarFragment extends Fragment implements
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText txt_empleado_rfc,txt_empleado_nombre,txt_empleado_ap,txt_empleado_am,txt_empleado_date,
            txt_empleado_tel,txt_empleado_correo,txt_empleado_password,txt_empleado_password2;
    private TextInputLayout tilNombre,tilRfc,tilAp,tilAm,tilCorreo,tilTelefono,tilDate,tilPass1,tilPass2;


    private String rfc,nombre,fecha_nacimiento,telefono,correo,password;
    private boolean a,b,c,d,e,f,g,h;

    private AppCompatSpinner spinner_sexo,spinner_puesto;
    List<String> listaGeneros,listaPuestos;
    ArrayAdapter<String> adapterSpinner,adapterSpinnerPuestos;
    private int dia,mes,anio;

    RequestQueue rq;
    JsonRequest jrq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_empleados_agregar, container, false);

        spinner_sexo = (AppCompatSpinner)view.findViewById(R.id.spn_add_empleado_sexo);
        spinner_puesto = (AppCompatSpinner)view.findViewById(R.id.spn_add_empleado_puesto);
        listaGeneros = new ArrayList<>();
        listaPuestos = new ArrayList<>();
        String[] generos = {"Masculino","Femenino"};
        String[] puestos = {"Administrador","Jefe de obra"};
        Collections.addAll(listaGeneros, generos);
        Collections.addAll(listaPuestos,puestos);
        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listaGeneros);
        adapterSpinnerPuestos = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listaPuestos);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerPuestos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sexo.setAdapter(adapterSpinner);
        spinner_puesto.setAdapter(adapterSpinnerPuestos);
        spinner_sexo.setSelection(0);
        spinner_puesto.setSelection(0);

        txt_empleado_rfc = (EditText)view.findViewById(R.id.txt_add_empleado_rfc);
        txt_empleado_nombre = (EditText)view.findViewById(R.id.txt_add_empleado_nombre);
        txt_empleado_ap = (EditText)view.findViewById(R.id.txt_add_empleado_ap);
        txt_empleado_am = (EditText)view.findViewById(R.id.txt_add_empleado_am);
        //txt_empleado_date = (EditText) view.findViewById(R.id.txt_empleado_date);
        txt_empleado_tel = (EditText)view.findViewById(R.id.txt_add_empleado_tel);
        txt_empleado_correo = (EditText)view.findViewById(R.id.txt_add_empleado_correo);
        txt_empleado_password = (EditText)view.findViewById(R.id.et_password1_recuperar);
        txt_empleado_password2 =(EditText)view.findViewById(R.id.et_password2_recuperar);
        tilRfc = (TextInputLayout)view.findViewById(R.id.til_add_empleado_rfc);
        tilNombre = (TextInputLayout)view.findViewById(R.id.til_add_empleado_nombre);
        tilAp = (TextInputLayout)view.findViewById(R.id.til_add_empleado_ap);
        tilAm = (TextInputLayout)view.findViewById(R.id.til_add_empleado_am);
        //tilDate = (TextInputLayout)view.findViewById(R.id.til_add_empleado_date);
        tilTelefono = (TextInputLayout)view.findViewById(R.id.til_add_empleado_tel);
        tilCorreo = (TextInputLayout)view.findViewById(R.id.til_add_empleado_correo);
        tilPass1 =(TextInputLayout)view.findViewById(R.id.til_password1);
        tilPass2 = (TextInputLayout)view.findViewById(R.id.til_password2);

       /// txt_empleado_date.setOnClickListener(this);

        implemetarTextWatcher();
        return view;
    }

  /*  @Override
    public void onClick(View view) { //boton agregar epleado, cancelar y desplegar el calendario en el campo fecha

        try{

            if(view==txt_empleado_date){
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        fecha_nacimiento =  i +"-"+(i1+1)+"-"+i2;
                        txt_empleado_date.setText(fecha_nacimiento);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"Error e date" + e.getMessage() ,Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    public void onErrorResponse(VolleyError error) { //en caso de ocurrir un error en l aoperacion
        Toast.makeText(getActivity(),"Error al agregar usuario" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {//en caso de que la operacion se lleve con exito
        Toast.makeText(getActivity(),"Empleado registrado",Toast.LENGTH_LONG).show();
    }

    private void registrarEmpleado(){ //se ejecuta la solicitud al web service alojado en el servidor
        //http://dissymmetrical-diox.xyz/agregarEmpleado.php
        // String url ="http://10.0.0.7/login/registrar_empleado.php?rfc=" +rfc +
        String url ="http://dissymmetrical-diox.xyz/agregarEmpleado.php?rfc=" +rfc +
                "&nombre="+nombre+"&f_nacimiento="+ fecha_nacimiento+
                "&telefono="+telefono+"&correo="+correo+"&passw="+password +
                "&puesto=" + spinner_puesto.getSelectedItem().toString()+
                "&sexo="+ spinner_sexo.getSelectedItem().toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    public void iniciarValores(){ //incializa los valores
        rfc = txt_empleado_rfc.getText().toString();
        nombre = txt_empleado_nombre.getText().toString() + " " + txt_empleado_ap.getText().toString() + " " +txt_empleado_am.getText().toString();
        telefono = txt_empleado_tel.getText().toString();
        fecha_nacimiento = fechaNacimiento(rfc);
        correo = txt_empleado_correo.getText().toString();
        password = txt_empleado_password.getText().toString();
    }


    public void limpiarCampos(){//setea los campos
        txt_empleado_rfc.setText("");
        txt_empleado_nombre.setText("");
        txt_empleado_ap.setText("");
        txt_empleado_am.setText("");
        txt_empleado_tel.setText("");
        txt_empleado_password.setText("");
        txt_empleado_correo.setText("");
        spinner_sexo.setSelection(0);
        spinner_puesto.setSelection(0);
    }

    public String fechaNacimiento(String rfc){  //obtener la fecha de nacimeinto del empleado con su rfc
        String fecha ="";
        String anio="",mes,dia;
        int j=1;
        for(int i=0; i<rfc.length(); i++){
            if(Character.isDigit(rfc.charAt(i)) && j<7){
                fecha+=rfc.charAt(i);
                if(j==2 || j==4)
                    fecha+="-";
                j++;
            }
        }
        String[] parts = fecha.split("-");
        anio = parts[0];
        mes = parts[1];
        dia=parts[2];
        int n = Integer.parseInt(anio);
        if(n<=99 && n>0)
            anio="19" + n;
        else
            anio="2000";
        fecha= anio+"-"+mes+"-"+dia;
        return fecha;
    }

    public boolean nombresRFC(){
        String rfclPaterno = ""+rfc.charAt(0) + rfc.charAt(1);
        String rfclMaterno =""+ rfc.charAt(2);
        String rfclNombre = ""+ rfc.charAt(3);

        String lPaterno = ""+txt_empleado_ap.getText().toString().trim().charAt(0) + txt_empleado_ap.getText().toString().trim()
                .charAt(1);
        String lMaterno = ""+ txt_empleado_am.getText().toString().trim().charAt(0);
        String lNombre = ""+ txt_empleado_nombre.getText().toString().trim().charAt(0);

        if((rfclPaterno.equalsIgnoreCase(lPaterno)) &&(rfclMaterno.equalsIgnoreCase(lMaterno))
                    && rfclNombre.equalsIgnoreCase(lNombre))
            return true;
        return false;
    }

    public void implemetarTextWatcher(){ //implementa un escuchador para la validacion de los datos en las cajas de texto
        txt_empleado_rfc.addTextChangedListener(new TextWatcher() {
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

        txt_empleado_nombre.addTextChangedListener(new TextWatcher() {
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

        txt_empleado_ap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                c = Validar.nombre(String.valueOf(s),tilAp);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_empleado_am.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                d = Validar.nombre(String.valueOf(s),tilAm);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_empleado_tel.addTextChangedListener(new TextWatcher() {
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
        txt_empleado_correo.addTextChangedListener(new TextWatcher() {
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



        txt_empleado_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                g = Validar.password(String.valueOf(s),tilPass1);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_empleado_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                h = Validar.passwords(txt_empleado_password.getText().toString().trim(),
                        String.valueOf(s),tilPass2);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean validaEntradas(){
        if(a&&b&&c&&d&&e&&f&&g&&h)
            return true;
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_modificar, menu);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_aceptar:
                iniciarValores();
                if(validaEntradas()){
                    if(nombresRFC()==false){
                        Toast.makeText(getActivity(),"RFC no coincide con el nombre" ,Toast.LENGTH_LONG).show();
                        return false;
                    }
                    rq = Volley.newRequestQueue(getActivity());
                    registrarEmpleado();
                    limpiarCampos();
                }else{
                    Toast.makeText(getActivity(),"Algunos campos son incorrectos" ,Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.nav_cancelar:
                getFragmentManager().beginTransaction().remove(this).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
