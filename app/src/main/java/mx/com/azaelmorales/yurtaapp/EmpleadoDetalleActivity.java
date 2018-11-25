package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmpleadoDetalleActivity extends AppCompatActivity {
    // TextView textViewRFC,textViewNombre,textViewFN,textViewTel,textViewCorreo,textViewPuesto,textViewSexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empleado);


        TextView textViewRFC = (TextView)findViewById(R.id.tVDetEmRFC);
        TextView textViewNombre = (TextView)findViewById(R.id.tVDetEmNombre);
        TextView textViewFN  = (TextView)findViewById(R.id.tVdDetEmFecha);
        TextView textViewTel  = (TextView)findViewById(R.id.tVdDetEmTel);
        TextView textViewCorreo  = (TextView)findViewById(R.id.tVdDetEmCorreo);
        TextView textViewPuesto  = (TextView)findViewById(R.id.tVdDetEmPuesto);
        TextView textViewSexo  = (TextView)findViewById(R.id.tVdDetEmSexo);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            textViewRFC.setText("RFC:" + b.getString("RFC"));
            textViewNombre.setText(b.getString("NOMBRE"));
            textViewFN.setText(b.getString("FECHAN"));
            textViewTel.setText(b.getString("TELEFONO"));
            textViewCorreo.setText(b.getString("CORREO"));
            textViewPuesto.setText(b.getString("PUESTO"));
            textViewSexo.setText(b.getString("SEXO"));
        }



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
