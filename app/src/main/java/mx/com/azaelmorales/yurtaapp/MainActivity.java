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
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements  Response.Listener<JSONObject>,Response.ErrorListener {
    Button btnLogin;
    TextInputEditText txt_usuario,txt_contraseña;
    TextInputLayout  textInputLayout;
    RequestQueue rq;
    JsonRequest jrq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btn_start);
        txt_usuario = (TextInputEditText) findViewById(R.id.text_input_user);
        txt_contraseña = (TextInputEditText) findViewById(R.id.text_input_password);
        textInputLayout= (TextInputLayout)findViewById(R.id.login_text_input_correo);
        rq = Volley.newRequestQueue(this);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);


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

    }

    public void clickStart(View v){
        //iniciarSesion();
        Intent home_activity = new Intent(getApplicationContext(),HomeActivity.class);
        finish();
        startActivity(home_activity);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"usuario no encontrado" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,"se encontro",Toast.LENGTH_LONG).show();
        Empleado empleado = new Empleado();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try{
            jsonObject = jsonArray.getJSONObject(0);

            empleado.setCorreo(jsonObject.optString("user"));

            Intent home_activity = new Intent(getApplicationContext(),HomeActivity.class);
            home_activity.putExtra(HomeActivity.correo,empleado.getCorreo());
            finish();

            startActivity(home_activity);
        }catch (JSONException e){}
    }

    private void iniciarSesion(){
        String url ="http://10.0.0.7/login/sesion.php?user=" + txt_usuario.getText().toString()+
                    "&password=" + txt_contraseña.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


}
