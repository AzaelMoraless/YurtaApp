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

import mx.com.azaelmorales.yurtaapp.utilerias.Validar;


public class MaterialAgregarFragment extends Fragment implements  View.OnClickListener,
        Response.Listener<JSONObject>,Response.ErrorListener {

    private EditText txt_material_nombre,txt_material_marca,txt_material_precio,txt_material_cantidad;
    private TextInputLayout tilnombre,tilmarca,tilprecio,tilcantidad;
    private String nomb,marca,precio,cantidad;
    private boolean a,b,c,d,e,f,g;
    private AppCompatSpinner spinner_tipo;
    private Button btnCancelar,btnAceptar;
    List<String> listatipo;
    ArrayAdapter<String> adapterSpinner;


    RequestQueue rq;
    JsonRequest jrq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_material_agregar, container, false);

        spinner_tipo = (AppCompatSpinner)view.findViewById(R.id.spn_add_material_puesto);
        listatipo = new ArrayList<>();
        String[] tipos = {"Petreos","Industriales"};

        Collections.addAll(listatipo, tipos);


        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listatipo);
        //adapterSpinnerPuestos = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listaPuestos);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapterSpinnerPuestos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo.setAdapter(adapterSpinner);
        //spinner_puesto.setAdapter(adapterSpinnerPuestos);
        spinner_tipo.setSelection(0);
        //spinner_puesto.setSelection(0);

        txt_material_nombre = (EditText)view.findViewById(R.id.txt_add_material_nombre);
        txt_material_marca = (EditText)view.findViewById(R.id.txt_add_material_marca);
        txt_material_cantidad= (EditText)view.findViewById(R.id.txt_add_material_cantidad);
        txt_material_precio = (EditText)view.findViewById(R.id.txt_add_material_precio);


        tilnombre = (TextInputLayout)view.findViewById(R.id.til_add_material_nombre);
        tilmarca = (TextInputLayout)view.findViewById(R.id.til_add_material_marca);
        tilcantidad = (TextInputLayout)view.findViewById(R.id.til_add_material_cantidad);
        tilprecio = (TextInputLayout)view.findViewById(R.id.til_add_material_precio);




        btnAceptar = (Button)view.findViewById(R.id.btn_add_material_aceptar);
        btnCancelar =(Button)view.findViewById(R.id.btn_add_material_cancelar);

        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        implemetarTextWatcher();


        //objeto RequestQueue
        rq = Volley.newRequestQueue(getActivity());



        return view;
    }

    @Override
    public void onClick(View view) { //boton agregar epleado, cancelar y desplegar el calendario en el campo fecha
         if(view==btnAceptar){
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
        String url ="000/registrom.php?nombre=" +nomb +
                "&tipo="+spinner_tipo.getSelectedItem().toString()+"&cantidad="+cantidad+
                "&marca="+marca+"&precio="+precio+"&estado_m=1";

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    public void iniciarValores(){ //incialza los valoresnomb,marca,precio,cantidad;
        nomb = txt_material_nombre.getText().toString();
        marca = txt_material_marca.getText().toString();
        precio = txt_material_precio.getText().toString();
        cantidad = txt_material_cantidad.getText().toString();
    }


    public void limpiarCampos(){//setea los campos
        txt_material_nombre.setText("");
        txt_material_marca.setText("");
        txt_material_precio.setText("");
        txt_material_cantidad.setText("");
        spinner_tipo.setSelection(0);
    }




    public void implemetarTextWatcher(){ //implementa un escuchador para la validacion de los datos en las cajas de texto
        txt_material_nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a = Validar.nombre(String.valueOf(s),tilnombre);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_material_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b = Validar.nombre(String.valueOf(s),tilcantidad);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_material_precio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                c = Validar.nombre(String.valueOf(s),tilprecio);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_material_marca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                d = Validar.nombre(String.valueOf(s),tilmarca);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public boolean validaEntradas(){
       if(a&&b&&c&&d)
            return true;
       return false;
    }
}
