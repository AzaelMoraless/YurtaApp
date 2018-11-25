package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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


public class EmpleadosAgregarFragment extends Fragment implements  View.OnClickListener,
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText txt_empleado_rfc,txt_empleado_nombre,txt_empleado_ap,txt_empleado_am,txt_empleado_date,
            txt_empleado_tel,txt_empleado_correo,txt_empleado_password;
    private TextInputLayout tilNombre,tilRfc,tilAp,tilAm,tilCorreo,tilTelefono,tilDate;
    private String rfc,nombre,fecha_nacimiento,telefono,correo,password;
    private boolean a,b,c,d,e,f,g;
    private AppCompatSpinner spinner_sexo,spinner_puesto;
    private Button btnCancelar,btnAceptar;
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
        txt_empleado_date = (EditText) view.findViewById(R.id.txt_empleado_date);
        txt_empleado_tel = (EditText)view.findViewById(R.id.txt_add_empleado_tel);
        txt_empleado_correo = (EditText)view.findViewById(R.id.txt_add_empleado_correo);
        txt_empleado_password = (EditText)view.findViewById(R.id.txt_add_empleado_pass);

        tilRfc = (TextInputLayout)view.findViewById(R.id.til_add_empleado_rfc);
        tilNombre = (TextInputLayout)view.findViewById(R.id.til_add_empleado_nombre);
        tilAp = (TextInputLayout)view.findViewById(R.id.til_add_empleado_ap);
        tilAm = (TextInputLayout)view.findViewById(R.id.til_add_empleado_am);
        tilDate = (TextInputLayout)view.findViewById(R.id.til_add_empleado_date);
        tilTelefono = (TextInputLayout)view.findViewById(R.id.til_add_empleado_tel);
        tilCorreo = (TextInputLayout)view.findViewById(R.id.til_add_empleado_correo);




        btnAceptar = (Button)view.findViewById(R.id.btn_add_empleado_aceptar);
        btnCancelar =(Button)view.findViewById(R.id.btn_add_empleado_cancelar);

        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        txt_empleado_date.setOnClickListener(this);

        implemetarTextWatcher();


        //objeto RequestQueue
        rq = Volley.newRequestQueue(getActivity());



        return view;
    }

    @Override
    public void onClick(View view) { //boton agregar epleado, cancelar y desplegar el calendario en el campo fecha
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
        }else if(view==btnAceptar){
            iniciarValores();
            if(validaEntradas()){
                registrarEmpleado();
                limpiarCampos();
            }else{
                Toast.makeText(getActivity(),"Algunos campos son incorrectos" ,Toast.LENGTH_LONG).show();
            }

        }else if(view==btnCancelar){
            limpiarCampos();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) { //en caso de ocurrir un error en l aoperacion
        Toast.makeText(getActivity(),"Error al agregar usuario" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {//en caso de que la operacion se lleve con exito
        Toast.makeText(getActivity(),"Empleado registrado",Toast.LENGTH_LONG).show();
    }

    private void registrarEmpleado(){ //se ejecuta la solicitud al web service alojado en el servidor
        String url ="http://10.0.0.7/login/registrar_empleado.php?rfc=" +rfc +
                "&nombre="+nombre+"&f_nacimiento="+ fecha_nacimiento+
                "&telefono="+telefono+"&correo="+correo+"&passw="+password +
                "&puesto=" + spinner_puesto.getSelectedItem().toString()+
                "&sexo="+ spinner_puesto.getSelectedItem().toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    public void iniciarValores(){ //incialza los valores
        rfc = txt_empleado_rfc.getText().toString();
        nombre = txt_empleado_nombre.getText().toString() + " " + txt_empleado_ap.getText().toString() + " " +txt_empleado_am.getText().toString();
        telefono = txt_empleado_tel.getText().toString();
        correo = txt_empleado_correo.getText().toString();
        password = txt_empleado_password.getText().toString();
    }


    public void limpiarCampos(){//setea los campos
        txt_empleado_rfc.setText("");
        txt_empleado_nombre.setText("");
        txt_empleado_ap.setText("");
        txt_empleado_am.setText("");
        txt_empleado_tel.setText("");
        txt_empleado_date.setText("");
        txt_empleado_password.setText("");
        txt_empleado_correo.setText("");
        spinner_sexo.setSelection(0);
        spinner_puesto.setSelection(0);
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
                e = Validar.nombre(String.valueOf(s),tilTelefono);
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

        txt_empleado_date.addTextChangedListener(new TextWatcher() {
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
        if(a&&b&&c&&d&&e&&f&&g)
            return true;
        return false;
    }
}
