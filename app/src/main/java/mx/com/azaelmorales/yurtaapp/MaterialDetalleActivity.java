package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MaterialDetalleActivity extends AppCompatActivity {
    // TextView textViewRFC,textViewNombre,textViewFN,textViewTel,textViewCorreo,textViewPuesto,textViewSexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_material);


        TextView textViewCodigo = (TextView)findViewById(R.id.tVDetmatcodigo);
        TextView textViewNombre = (TextView)findViewById(R.id.tVdDetmatnombre);
        TextView textViewMarca  = (TextView)findViewById(R.id.tVdDetMatmarca);
        TextView textViewTipo  = (TextView)findViewById(R.id.tVdDetMattipo);
        TextView textViewCantidad  = (TextView)findViewById(R.id.tVdDetMatcant);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            textViewCodigo.setText("codigo:" + b.getString("CODIGO"));
            textViewNombre.setText(b.getString("NOMBRE"));
            textViewMarca.setText(b.getString("MARCA"));
            textViewTipo.setText(b.getString("TIPO"));
            textViewCantidad.setText(b.getString("CANTIDAD"));
            //textViewPuesto.setText(b.getString("PUESTO"));
            //textViewSexo.setText(b.getString("SEXO"));
        }



        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_panel_material);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Material");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}
