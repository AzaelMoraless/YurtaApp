package mx.com.azaelmorales.yurtaapp;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import me.anwarshahriar.calligrapher.Calligrapher;
import mx.com.azaelmorales.yurtaapp.utilerias.Preferences;
import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements  Response.Listener<JSONObject>,Response.ErrorListener {
    Button btnLogin;
    TextInputEditText txt_usuario,txt_contraseña;
    TextInputLayout  textInputLayout;
    TextView textViewRecuperar;
    RequestQueue rq;
    JsonRequest jrq;
    RadioButton radioButton;
    private boolean isCheckedRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //verificar si ya esta logeado
        if(Preferences.getPeferenceBoolean(this,Preferences.PREFERENCE_ESTADO_SESION)){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }


        btnLogin = (Button) findViewById(R.id.btn_start);
        txt_usuario = (TextInputEditText) findViewById(R.id.text_input_user);
        txt_contraseña = (TextInputEditText) findViewById(R.id.text_input_password);
        textInputLayout= (TextInputLayout)findViewById(R.id.login_text_input_correo);
        radioButton = (RadioButton)findViewById(R.id.RBSesion);
        textViewRecuperar = (TextView)findViewById(R.id.textViewOlvidePassword);
        rq = Volley.newRequestQueue(this);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        isCheckedRB= radioButton.isChecked();

        //radio button mantener sesion
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheckedRB){
                    radioButton.setChecked(false);
                }
                isCheckedRB = radioButton.isChecked();
            }
        });
        //opcion recuperar contraseña
        textViewRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //validar correo
        txt_usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Validar.correo(String.valueOf(s),textInputLayout);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"OpenSans-Regular.ttf",true);

    }

    //iniciar sesion
    public void clickStart(View v){
        iniciarSesion();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"usuario no encontrado" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Preferences.savePreferenceBoolean(MainActivity.this,radioButton.isChecked(),
                Preferences.PREFERENCE_ESTADO_SESION);
        ///Toast.makeText(this,"se encontro",Toast.LENGTH_LONG).show();
        Empleado empleado = new Empleado();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject;

        try{
            jsonObject = jsonArray.getJSONObject(0);
            empleado.setCorreo(jsonObject.optString("correo"));
            empleado.setNombre(jsonObject.optString("nombre_e"));
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            //guarda los valores de nombre y correo
            Preferences.savePreferenceString(MainActivity.this,
                    empleado.getNombre(),Preferences.PREFERENCE_EMPLEADO_NOMBRE);
            Preferences.savePreferenceString(MainActivity.this,
                    empleado.getCorreo(),Preferences.PREFERENCE_EMPLEADO_CORREO);
            startActivity(intent);
            finish();
        }catch (JSONException e){
            Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarSesion(){
        String url ="http://dissymmetrical-diox.xyz/sesion.php?user=" + txt_usuario.getText().toString()+
                    "&password=" + txt_contraseña.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


}
