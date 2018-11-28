package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EmpleadosEditarActivity extends AppCompatActivity {
    private EditText editTextRFC,editTextNombre,editTextAp,editTextAm,editTextFechaN
                        ,editTextTelefono,editTextCorreo,editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_empleados_editar);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_panel_empleados);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Editar empleados");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
/*

        editTextRFC = (EditText)findViewById(R.id.etEmpleadoMoRFC);
        editTextNombre = (EditText)findViewById(R.id.etEmpleadoMoNombre);
       */
/* editTextAp = (EditText)findViewById(R.id.etEmpleadoMoAp);
        editTextAm =(EditText)findViewById(R.id.etEmpleadoMoAM);*//*




        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            */
/*String[] datos = b.getString("NOMBRE").split(" ");
            String nombre,apellidoP,apellidoM;
            nombre = datos[0];
            apellidoP = datos[1];
            apellidoM = datos[2];*//*

            editTextRFC.setText( "AZaeee");
            editTextRFC.setKeyListener(null);
            editTextNombre.setText("AZAElll");
         */
/*   editTextAp.setText(apellidoP);
            editTextAm.setText(apellidoM);*//*

            */
/*
            ///editTextFechaN.setText(b.getString("FECHAN"));
            editTextTelefono.setText(b.getString("TELEFONO"));
            editTextCorreo.setText(b.getString("CORREO"));
            editTextPassword.setText(b.getString("PASSWORD"));*//*

            }
*/

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

                return true;
            case R.id.nav_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

