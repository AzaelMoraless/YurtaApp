package mx.com.azaelmorales.yurtaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class AlmacenActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados, new EmpleadosViewFragment()).addToBackStack(null).commit();
            FragmentManager fragmentManager=getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.lista_mat:
                   fragmentManager.beginTransaction().replace(R.id.fragment_content_material, new MaterialViewFragment()).addToBackStack(null).commit();

                    return  true;
                case R.id.agregar_mat:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_material,new MaterialAgregarFragment()).addToBackStack(null).commit();
                    return  true;

                case R.id.editar_mat:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_material,new MaterialEditarFragment()).addToBackStack(null).commit();
                    return  true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation_mat);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_panel_material);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Almacen");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });

    }

}
