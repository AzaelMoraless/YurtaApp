package mx.com.azaelmorales.yurtaapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class PedidosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_pedidos);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_mod_pedidos);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pedidos");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_lista_pedidos:

                    return true;
                case R.id.navigation_agregar_pedido:
                   /* fragmentManager.beginTransaction().replace(R.id.fragment_content_empleados,
                            new EmpleadosAgregarFragment()).addToBackStack(null).commit();*/
                    return true;
                case R.id.navigation_modificar_pedido:

                    return true;
            }
            return false;
        }
    };
}
