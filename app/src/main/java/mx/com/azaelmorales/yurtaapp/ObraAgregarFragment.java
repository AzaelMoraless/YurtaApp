package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;

import mx.com.azaelmorales.yurtaapp.utilerias.Preferences;
import mx.com.azaelmorales.yurtaapp.utilerias.Servidor;
import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

import static android.app.Activity.RESULT_OK;

public class ObraAgregarFragment extends Fragment implements  View.OnClickListener,
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText editTextFolio,editTextNombre,editTextDependencia,editTextLugar,
                        editTextFechaInicio;

    private TextInputLayout textInputLayoutNombre,textInputLayoutDependencia,textInputLayoutLugar,
                            textInputLayoutFecha;
    private AppCompatSpinner spinnerTipo;
    List<String> listaTipos;
    ArrayAdapter<String> adapterSpinnerTipos;
    private int dia,mes,anio;
    private String fecha,nombre,idObra,dependencia,lugar,tipo;
    private Button buttonAgregarMaterial;
    private boolean a,flagb,c,d;
    static ArrayList<Material> arrayListMateriales;
    static  ListView listViewMaterialesPedidos;
    TextView textViewTitle;
    ScrollView scrollView;
    int estado;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_obra_agregar, container, false);
        listViewMaterialesPedidos = (ListView)view.findViewById(R.id.listViewMaterialesPedidos);
        editTextFolio = (EditText)view.findViewById(R.id.et_folio_obra);
        editTextNombre =(EditText)view.findViewById(R.id.et_nombre_obra);
        editTextDependencia =(EditText)view.findViewById(R.id.et_dependencia_obra);
        editTextLugar=(EditText)view.findViewById(R.id.et_lugar_obra);
        editTextFechaInicio =(EditText)view.findViewById(R.id.et_fecha_obra);

        textInputLayoutNombre =(TextInputLayout)view.findViewById(R.id.til_nombre_obra);
        textInputLayoutDependencia =(TextInputLayout)view.findViewById(R.id.til_dependencia_obra);
        textInputLayoutLugar =(TextInputLayout)view.findViewById(R.id.til_lugar_obra);
        textInputLayoutFecha =(TextInputLayout)view.findViewById(R.id.til_fecha_obra);

        spinnerTipo = (AppCompatSpinner)view.findViewById(R.id.spn_tipo_obra);
        listaTipos = new ArrayList<>();
        String[] tipos = {"Pavimentacion","Techado","Agua potable"};
        Collections.addAll(listaTipos, tipos);
        adapterSpinnerTipos = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listaTipos);
        adapterSpinnerTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterSpinnerTipos);
        spinnerTipo.setSelection(0);

        buttonAgregarMaterial = (Button)view.findViewById(R.id.buttom_agregar_material);

        buttonAgregarMaterial.setOnClickListener(this);
        editTextFechaInicio.setOnClickListener(this);

        iniciarValor();
        editTextFolio.setKeyListener(null);
        ///arrayListMateriales = new ArrayList<Material>();

        scrollView = (ScrollView)view.findViewById(R.id.scroll);

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.findViewById(R.id.listViewMaterialesPedidos).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        listViewMaterialesPedidos.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        implemetarTextWatcher();
        textViewTitle =(TextView)view.findViewById(R.id.titleObra);
        return view;
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
                //guardar los datos en la tabla obra pedido y detalle pedido
                if(arrayListMateriales!=null) {
                    if(validarEntradas()){
                        agregarObra();
                        getFragmentManager().beginTransaction().remove(this).commit();
                    }
                    else
                        Toast.makeText(getActivity(),"Datos incorrectos" ,Toast.LENGTH_LONG).show();
                }
                else
                        Toast.makeText(getActivity(),"Agregue material a la obra" ,Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_cancelar:
                getFragmentManager().beginTransaction().remove(this).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==editTextFechaInicio){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int i, int i1, int i2) {
                    fecha =  i +"-"+(i1+1)+"-"+i2;
                    editTextFechaInicio.setText(fecha);
                }
            },anio,mes,dia);
            datePickerDialog.show();
        }
        if(view==buttonAgregarMaterial){
            Intent intent =new Intent(view.getContext(),ObraAgregarMaterialActivity.class);
            intent.putExtra("ID",editTextFolio.getText().toString().trim());
            intent.putExtra("LUGAR",editTextLugar.getText().toString().trim());
            intent.putExtra("TIPO",spinnerTipo.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(),"Error" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Obra obra;
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject;
        try{
            jsonObject = jsonArray.getJSONObject(0);
            obra = new Obra(jsonObject.optString("id_obra"));
            int idO = Integer.parseInt(obra.getId()) + 1;
            idObra = idO+"";
            editTextFolio.setText(""+idO);
        }catch (JSONException e){
            Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarValor(){
        String url ="http://dissymmetrical-diox.xyz/idObra.php";
        RequestQueue rq;
        JsonRequest jrq;
        rq = Volley.newRequestQueue(getActivity());
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    public void inicializarValores(){
        nombre = editTextNombre.getText().toString().trim();
        dependencia = editTextDependencia.getText().toString().trim();
        lugar = editTextLugar.getText().toString().trim();
        tipo = spinnerTipo.getSelectedItem().toString();
        estado =0;
    }

    public void agregarObra(){ //registrar la obra en la base de datos
       /// inicializarValores();
        String rfc = Preferences.getPeferenceString(getActivity(),Preferences.PREFERENCE_EMPLEADO_RFC);
        String url = "http://dissymmetrical-diox.xyz/agregarObra.php?nombre="+nombre+"&dependencia="+dependencia+
                "&lugar="+lugar+"&fecha="+fecha+"&tipo="+tipo+"&encargado="+rfc;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                agregarPedido(response);
                enviarNotificacion();
                Toast.makeText(getActivity(),"Registro insertado :)" ,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Erro al insertar" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);


    }

    public void agregarPedido(String id){//registrar el pedido de la obra en la base de datos
        ////iniciarValor();
        String urlPedido = "http://dissymmetrical-diox.xyz/registrarPedido.php?fecha1="+fecha+"&fecha2=&estado=0&id_o="+id;
        RequestQueue requestQueuePedido = Volley.newRequestQueue(getContext());
        StringRequest stringRequestPedido = new StringRequest(Request.Method.GET, urlPedido, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String codigo,cantidad;

                for(int i=0; i<arrayListMateriales.size(); i++){
                        codigo = arrayListMateriales.get(i).getCodigo();
                        cantidad = arrayListMateriales.get(i).getCantidadSolicitada();

                        agregarDetallePedido(response,codigo,cantidad);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error al registrar el pedido" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueuePedido.add(stringRequestPedido);
    }

    public void agregarDetallePedido(String folio,String codigo,String cantidad){ //registr el detalle del pedido
        String url = "http://dissymmetrical-diox.xyz/registrarDetPedido.php?folio="+folio+"&codigo="+codigo
                                +"&cantidad="+cantidad;
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error al registrar el pedido" ,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

    }






    public void implemetarTextWatcher(){
        editTextNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                a = Validar.nombre(String.valueOf(s),textInputLayoutNombre);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDependencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                flagb = Validar.nombre(String.valueOf(s),textInputLayoutDependencia);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                c = Validar.nombre(String.valueOf(s),textInputLayoutLugar);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

            editTextFechaInicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                d = Validar.fecha_nac(String.valueOf(s),textInputLayoutFecha);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public boolean validarEntradas(){
        if(a&&flagb&&c&&d)
            return true;
        else
            return false;
    }

    public void enviarNotificacion(){
        NotificationCompat.Builder nBuilder;
        NotificationManager nNotiMgr =(NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);;

        int icono = R.mipmap.ic_launcher;
        ////Intent intent= new Intent(PedidosActivity.this, ResulAtivity.class);
        ///PendingIntent resulPedint = PendingIntent.getActivity(MainActivity.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder= new NotificationCompat.Builder(getActivity())
                ///.setContentIntent(resulPedint)
                .setSmallIcon(icono)
                .setContentTitle("YutaApp")
                .setContentText("Tienes un nuevo pedido")
                .setVibrate(new long[]{100,250,100,500})
                .setAutoCancel(true);

        nNotiMgr.notify(1,nBuilder.build());
    }
}
