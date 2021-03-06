package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import me.anwarshahriar.calligrapher.Calligrapher;

public class EmpleadosActivity extends AppCompatActivity {
    private Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager=getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_lista_empleados:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados,
                            new EmpleadosViewFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_agregar_empleado:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados,
                            new EmpleadosAgregarFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_editar_empleado:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados,
                            new EmpleadosModificarFragment()).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_empleados);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_empleados);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_panel_empleados);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Empleados");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });



            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados, new EmpleadosInicioFragment()).commit();
        }catch(Exception e){
            Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

}
