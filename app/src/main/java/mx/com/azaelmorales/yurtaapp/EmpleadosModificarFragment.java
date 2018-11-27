package mx.com.azaelmorales.yurtaapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class EmpleadosModificarFragment extends Fragment {
    private TextView textView;
    public EmpleadosModificarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empleados_modificar, container, false);

        textView = (TextView)view.findViewById(R.id.texto_modificar);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_modificar, menu); // TU MENU


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_aceptar: // TU OPCION
                textView.setText("aceptar cambios");
                return true;
            case R.id.nav_cancelar: // TU OPCION
                textView.setText("cancelar cambios");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
