package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmpleadoDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empleado);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_panel_empleados);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Empleados");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///startActivity(new Intent(getApplicationContext(),EmpleadosViewFragment.class));
                finish();
            }
        });


    }
}
