package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador_mat extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    String [][] datos;
    public Adaptador_mat(Context context, String [][] datos){
        this.context = context;
        this.datos = datos;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.elemento_material,null);
        TextView textViewNombre = vista.findViewById(R.id.textViewnombremat);
        TextView textViewMarca = vista.findViewById(R.id.textViewmarca);
        TextView textViewTiempo = vista.findViewById(R.id.textViewtipo);
        textViewNombre.setText("Nombre: " + datos[i][1]);
        textViewMarca.setText("Marca:" + datos[i][4]);
        textViewTiempo.setText("Tipo:" + datos[i][2]);
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
