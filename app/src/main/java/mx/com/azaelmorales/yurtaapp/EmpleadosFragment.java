package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class EmpleadosFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private TextView txt_empleados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_empleados, container, false);
        txt_empleados = (TextView) view.findViewById(R.id.txt_empleados);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation_empleados);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();

                int id = item.getItemId();
                if(id==R.id.buscar_empleado){
                    txt_empleados.setText("Buscar empleado");

                }else if(id==R.id.agregar_empleado){

                    fragmentManager.beginTransaction().replace(R.id.contenedor, new EmpleadosAgregarFragment()).addToBackStack(null).commit();
                    //getSupportActionBar().setTitle("Obras");
                }
                return false;
            }
        });
        return view;
    }
}
