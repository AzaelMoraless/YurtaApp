package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ObrasActivity extends AppCompatActivity {

    private static Context context;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_lista_obras:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_obras,
                            new ObrasViewFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_agregar_obra:
                     fragmentManager.beginTransaction().replace(R.id.fragment_content_obras,
                     new ObraAgregarFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_modificar_obra:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content_obras,
                            new ObraModificarFragment()).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_obras);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_obras);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Obras");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ///super.onCreate();
        ObrasActivity.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ObrasActivity.context;
    }

}
