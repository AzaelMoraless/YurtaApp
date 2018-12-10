package mx.com.azaelmorales.yurtaapp;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessageAware;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.anwarshahriar.calligrapher.Calligrapher;
import mx.com.azaelmorales.yurtaapp.utilerias.GeneratePassword;
import mx.com.azaelmorales.yurtaapp.utilerias.Preferences;
import mx.com.azaelmorales.yurtaapp.utilerias.Servidor;
import mx.com.azaelmorales.yurtaapp.utilerias.Validar;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements  Response.Listener<JSONObject>,Response.ErrorListener {
    Button btnLogin;
    TextInputEditText txt_usuario,txt_contraseña;
    TextInputLayout  textInputLayout;
    TextView textViewRecuperar;
    private String correoYurtaApp="yurtaapp@gmail.com";
    private String passwordYurtaApp = "yurtaPrueba";
    RequestQueue rq;
    JsonRequest jrq;
    RadioButton radioButton;
    private boolean isCheckedRB;
    String correoRecuperacion;
    Session session;
    private ProgressBar progressBar;
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


        progressBar = (ProgressBar)findViewById(R.id.progressMain);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);


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


                final EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Recuperar contraseña")
                        .setMessage("Ingrese su correo")
                        .setView(taskEditText)
                        .setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String correo = taskEditText.getText().toString().trim();
                                if(Validar.correo(correo)){
                                   buscarCorreo(correo);
                                }else{
                                    Toast.makeText(MainActivity.this,"Correo invalido" ,Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();



               /* AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Write your message here.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();*/

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
        new Asynctask_load().execute();
        progressBar.setVisibility(View.VISIBLE);
        ///Toast.makeText(this,"se encontro",Toast.LENGTH_LONG).show();
        Empleado empleado = new Empleado();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject;

        try{

            jsonObject = jsonArray.getJSONObject(0);
            empleado.setCorreo(jsonObject.optString("correo"));
            empleado.setNombre(jsonObject.optString("nombre_e"));
            empleado.setRfc(jsonObject.optString("rfc"));
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            //guarda los valores de nombre y correo
            Preferences.savePreferenceString(MainActivity.this,
                    empleado.getNombre(),Preferences.PREFERENCE_EMPLEADO_NOMBRE);
            Preferences.savePreferenceString(MainActivity.this,
                    empleado.getCorreo(),Preferences.PREFERENCE_EMPLEADO_CORREO);
            Preferences.savePreferenceString(MainActivity.this,
                    empleado.getRfc(),Preferences.PREFERENCE_EMPLEADO_RFC);
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

    public void buscarCorreo(final String correo2){
        String urlBuscar = "http://dissymmetrical-diox.xyz/buscarCorreo.php?correo="+correo2;

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuscar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if(response.equals("[]"))
                        Toast.makeText(MainActivity.this,"El correo no esta registrado" ,Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(MainActivity.this,"Revisa tu correo" ,Toast.LENGTH_LONG).show();
                        enviarCorreo(correo2);
                        Intent intent = new Intent(getApplicationContext(),RecuperarPasswordActivity.class);
                        intent.putExtra("CORREO",correo2);
                        startActivity(intent);


                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 =
                        Toast.makeText(MainActivity.this,
                                "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

                toast1.show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void enviarCorreo(String correoDestino){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoYurtaApp,passwordYurtaApp);
                }
            });

            if(session!=null){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoYurtaApp));
                message.setSubject("YurtaApp codigo de recuperacion de contraseña");      //correo que se le envia
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correoDestino));
                String codigo = GeneratePassword.getPassword(
                        GeneratePassword.MINUSCULAS+
                                GeneratePassword.MAYUSCULAS+
                                GeneratePassword.ESPECIALES,8);
                message.setContent(codigo,"text/html; charset=utf8");
                Transport.send(message);
                setPassword(correoDestino,codigo); //modifica el codigo de recuperacion del usuario en la base daatos

            }

        }catch (Exception e){}


    }


    public void setPassword(String correo,String codigo){
        String urlBuscar = "http://dissymmetrical-diox.xyz/cambiarCodigoRecuperacion.php?correo="+correo+"&codigo="+codigo;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuscar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 =
                        Toast.makeText(MainActivity.this,
                                "Error al cargar los datos"+ error.getMessage(), Toast.LENGTH_LONG);

                toast1.show();
            }
        });
        requestQueue.add(stringRequest);
    }



    public class Asynctask_load extends AsyncTask<Void,Integer,Void> {
        int progreso;
        @Override
        protected void onPreExecute(){
            /// Toast.makeText(getActivity(),"Cargando",Toast.LENGTH_LONG).show();
            progreso =0;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            while(progreso<50){
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(20);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Void result){
            ///Toast.makeText(getActivity(),"onPostExeecute",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
}