package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ObraDetalleActivity extends AppCompatActivity {
    private Obra obra;
    private ImageView buttonEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalle);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar_obras_det);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Material");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        TextView textViewID = (TextView)findViewById(R.id.tVEditIDobra);
        TextView textViewNombre = (TextView)findViewById(R.id.tVEditNombreObra);
        TextView textViewDependencia = (TextView)findViewById(R.id.tVEditDepenObra);
        TextView textViewLugar =(TextView)findViewById(R.id.tVEditLugarObra);
        TextView textViewFecha =(TextView)findViewById(R.id.tVEditFechaObra);
        TextView textViewTipo =(TextView)findViewById(R.id.tVEditTipoObra);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            String id,nombre,dependencia,lugar,fecha,tipo;
            id = b.getString("ID");
            nombre = b.getString("NOMBRE");
            dependencia = b.getString("DEPENDENCIA");
            lugar = b.getString("LUGAR");
            fecha =b.getString("FECHAINI");
            tipo = b.getString("TIPO");

            obra = new Obra(id,nombre,dependencia,lugar,fecha,tipo);

            textViewID.setText("ID: " + id);
            textViewNombre.setText(nombre);
            textViewDependencia.setText(dependencia);
            textViewLugar.setText(lugar);
            textViewFecha.setText(fecha);
            textViewTipo.setText("Tipo: " +tipo);
        }


        buttonEditar = (ImageView)findViewById(R.id.button_editar);
    }
}
