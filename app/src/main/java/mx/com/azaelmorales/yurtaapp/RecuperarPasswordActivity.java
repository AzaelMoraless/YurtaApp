package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

public class RecuperarPasswordActivity extends AppCompatActivity implements
        Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText editTextCodigo,editTextPassword1,editTextPassword2;
    private TextInputLayout textInputLayoutPass1,textInputLayoutPass2;
    private String correo,codigo,newPassword;
    private Button buttonCambiar;
    private boolean flagb,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_recuperar_password);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Recuperar contraseña");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
             correo = b.getString("CORREO");
        }


        editTextCodigo = (EditText)findViewById(R.id.et_codigo_recuperacion);
        editTextPassword1 =(EditText)findViewById(R.id.et_password1_recuperar);
        editTextPassword2 =(EditText)findViewById(R.id.et_password2_recuperar);

        textInputLayoutPass1 =(TextInputLayout)findViewById(R.id.til_password1);
        textInputLayoutPass2 =(TextInputLayout)findViewById(R.id.til_password2);
        buttonCambiar = (Button)findViewById(R.id.button_cambiar);


        editTextPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flagb = Validar.password(String.valueOf(s),textInputLayoutPass1);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                c = Validar.passwords(editTextPassword1.getText().toString().trim(),
                        String.valueOf(s),textInputLayoutPass2);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaDatos()&&!editTextCodigo.getText().toString().toString().equals("")){
                    inicializarValores();
                    cambiarPassword(correo,codigo);
                }else{
                    Toast.makeText(RecuperarPasswordActivity.this,"Error en los datos" ,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void cambiarPassword(String correo,String codigo){

            String urlBuscar = "http://dissymmetrical-diox.xyz/buscarCodigoRecuperacion.php?correo="+correo+"&codigo="+codigo;

            RequestQueue requestQueue = Volley.newRequestQueue(RecuperarPasswordActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuscar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("[]"))
                        Toast.makeText(RecuperarPasswordActivity.this,"Codigo de recuperacion incorrecto" ,Toast.LENGTH_LONG).show();
                    else{
                        actualizarPassword();
                        Toast.makeText(RecuperarPasswordActivity.this,"Contraseña actualizada",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast1 =
                            Toast.makeText(RecuperarPasswordActivity.this,
                                    "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

                    toast1.show();
                }
            });
            requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
       /// Toast.makeText(this,"Error al actualizar" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject   response) {
        Toast.makeText(this,"Contraseña actualizada",Toast.LENGTH_LONG).show();
    }

    private void actualizarPassword(){
        RequestQueue rq;
        JsonRequest jrq;
        rq = Volley.newRequestQueue(this);
        String url ="http://dissymmetrical-diox.xyz/cambiarPassword.php?correo="+correo+"&newpass="+newPassword+
                    "&codigo="+codigo;
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    public void inicializarValores(){
        newPassword = editTextPassword1.getText().toString().trim();
        codigo = editTextCodigo.getText().toString().trim();
    }

    public boolean validaDatos(){
        if(flagb&&c)
            return true;
        return false;
    }

}
