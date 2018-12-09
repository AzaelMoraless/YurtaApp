package mx.com.azaelmorales.yurtaapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import me.anwarshahriar.calligrapher.Calligrapher;
import mx.com.azaelmorales.yurtaapp.utilerias.Preferences;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView txt_correo_panel;
    private TextView txt_nombre_panel;

    private ImageView imgFondo;
    private TextView textViewCorreo;
    private String correo;
    private String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* FragmentManager fragmentManager=getSupportFragmentManager();  //fragment que inicia
        fragmentManager.beginTransaction().replace(R.id.contenedor, new InicioFragment()).addToBackStack(null).commit();
        getSupportActionBar().setTitle("Inicio");*/


        imgFondo = (ImageView)findViewById(R.id.img_logofomdo);
        int alphaAmount = 128; // Some value 0-255 where 0 is fully transparent and 255 is fully opaque
        imgFondo.setAlpha(alphaAmount);
         //pasar valores al segundo activity
           /* Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if(b!=null) {
                correo = b.getString("CORREO");
                nombre = b.getString("NOMBRE");

            }*/
            correo = Preferences.getPeferenceString(this,Preferences.PREFERENCE_EMPLEADO_CORREO);
            nombre = Preferences.getPeferenceString(this,Preferences.PREFERENCE_EMPLEADO_NOMBRE);
    }

   /* @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();//No se porqué puse lo mismo O.o
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        txt_correo_panel= (TextView)findViewById(R.id.txt_panel_correo);
        txt_nombre_panel = (TextView)findViewById(R.id.txt_panel_nombre);
        txt_correo_panel.setText(correo);
        txt_nombre_panel.setText(nombre);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"OpenSans-Regular.ttf",true);
        return true;
    }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_obras) {
            Intent activity = new Intent(getApplicationContext(),ObrasActivity.class);
            startActivity(activity);
        } else if (id == R.id.nav_almacen) {
          //  fragmentManager.beginTransaction().replace(R.id.contenedor, new AlmacenFragment()).addToBackStack(null).commit();
            //getSupportActionBar().setTitle("Almacen");
            Intent intent = new Intent(getApplicationContext(),AlmacenActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_pedidos){
            Intent intent = new Intent(getApplicationContext(),PedidosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_empleado) {
            Intent empleados_activity = new Intent(getApplicationContext(),EmpleadosActivity.class);
            startActivity(empleados_activity);
        } else if (id == R.id.nav_manual) {

        } else if (id == R.id.nav_acerca ){
          Intent activity = new Intent(getApplicationContext(),AboutActivity.class);
          startActivity(activity);
        }else if(id ==R.id.nav_cerrar_sesion){


            Preferences.savePreferenceBoolean(HomeActivity.this,false,Preferences.PREFERENCE_ESTADO_SESION);
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"OpenSans-Regular.ttf",true);
        return true;
    }
}
