package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EmpleadoDetalleActivity extends AppCompatActivity {
    // TextView textViewRFC,textViewNombre,textViewFN,textViewTel,textViewCorreo,textViewPuesto,textViewSexo;
    private Empleado empleado;
    private ImageView buttonEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empleado);

        TextView textViewRFC = (TextView)findViewById(R.id.tVDetEmRFC);
        TextView textViewNombre = (TextView)findViewById(R.id.tVDetEmNombre);
        TextView textViewFN  = (TextView)findViewById(R.id.tVdDetEmFecha);
        TextView textViewTel  = (TextView)findViewById(R.id.tVdDetEmTel);
        TextView textViewCorreo  = (TextView)findViewById(R.id.tVEditFechaObra);
        TextView textViewPuesto  = (TextView)findViewById(R.id.tVdDetEmPuesto);
        TextView textViewSexo  = (TextView)findViewById(R.id.tVdDetEmSexo);
        TextView textViewEstado = (TextView)findViewById(R.id.tVdDetEmEstado);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();



        if(b!=null){
            String nombre,rfc,fechaN,telefono,correo,puesto,sexo,estado,password;
            nombre = b.getString("NOMBRE");
            rfc = b.getString("RFC");
            fechaN = b.getString("FECHAN");
            telefono = b.getString("TELEFONO");
            correo = b.getString("CORREO");
            puesto = b.getString("PUESTO");
            sexo = b.getString("SEXO");
            estado = "Activo";
            password = b.getString("PASSWORD");

            //creamos un objeto de tipo empleado para almacenar los datos recuperados del bundle
            empleado = new Empleado(rfc,puesto,sexo,telefono,fechaN,nombre,correo,estado,password);
            //seteamos los textviews a mostrar
            textViewRFC.setText("RFC:" +rfc);
            textViewNombre.setText(nombre);
            textViewFN.setText(fechaN);
            textViewTel.setText(telefono);
            textViewCorreo.setText(correo);
            textViewPuesto.setText(puesto);
            textViewSexo.setText(sexo);
            textViewEstado.setText("Activo");
        }



        buttonEditar = (ImageView)findViewById(R.id.button_editar);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(empleado!=null){
                    Intent intent2 = new Intent(getApplicationContext(),EmpleadosEditarActivity.class);
                    //almacena de nuevo en el bundle los valores para mostrarlos en el activity editar empleado

                    intent2.putExtra("RFC",empleado.getRfc());
                    intent2.putExtra("PUESTO",empleado.getPuesto());
                    intent2.putExtra("SEXO",empleado.getSexo());
                    intent2.putExtra("TELEFONO",empleado.getTelefono());
                    intent2.putExtra("FECHAN",empleado.getFechaNacimiento());
                    intent2.putExtra("NOMBRE",empleado.getNombre());
                    intent2.putExtra("CORREO",empleado.getCorreo());
                    intent2.putExtra("PASSWORD",empleado.getPassword());
                    startActivity(intent2);
                }

            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_panel_empleados);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Empleados");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
