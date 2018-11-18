package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mx.com.azaelmorales.yurtaapp.R;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    String [][] datos;
    public Adaptador(Context context,String [][] datos){
        this.context = context;
        this.datos = datos;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.elemento_lista,null);
        TextView textViewNombre = vista.findViewById(R.id.textViewNombre);
        TextView textViewCorreo = vista.findViewById(R.id.textViewcorreo);
        TextView textViewRfc = vista.findViewById(R.id.textViewRfc);

        textViewNombre.setText("Nombre: " + datos[i][0]);
        textViewRfc.setText("RFC:" + datos[i][1]);
        textViewCorreo.setText("Correo:" + datos[i][2]);
        return vista;
    }
    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
