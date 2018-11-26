package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.com.azaelmorales.yurtaapp.R;

public class Adaptador extends BaseAdapter implements Filterable{
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<Empleado> empleados;
    CustomFilter filter;
    ArrayList<Empleado> filterList;



    public Adaptador(Context context,ArrayList<Empleado> empleados){
        this.context = context;
        this.empleados = empleados;
        this.filterList = empleados;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.elemento_lista,null);
        TextView textViewNombre = vista.findViewById(R.id.textViewNombre);
        TextView textViewCorreo = vista.findViewById(R.id.textViewcorreo);
        TextView textViewRfc = vista.findViewById(R.id.textViewRfc);

        textViewNombre.setText("Nombre:"+ empleados.get(i).getNombre());
        textViewRfc.setText("RFC:"+ empleados.get(i).getRfc());
        textViewCorreo.setText("Correo:"+ empleados.get(i).getCorreo());
        return vista;
    }
    @Override
    public int getCount() {
        return empleados.size();
    }

    @Override
    public Object getItem(int pos) {
        //return empleados.get(pos);
        return null;
    }

    @Override
    public long getItemId(int pos) {
        //return empleados.indexOf(getItemId(pos));
        return 0;
    }
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter= new CustomFilter();
        }
        return  filter;
    }

    class CustomFilter extends  Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Empleado> filters = new ArrayList<Empleado>();
                for (int i=0; i<filterList.size(); i++){
                    if(filterList.get(i).getNombre().toUpperCase().contains(constraint)){
                        Empleado e = new Empleado(filterList.get(i).getNombre(),filterList.get(i).getRfc(),
                                filterList.get(i).getCorreo());
                        filters.add(e);
                    }
                }
                results.count = filters.size();
                results.values= filters;

            }else{
                results.count = filterList.size();
                results.values= filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            empleados = (ArrayList<Empleado>) results.values;
            notifyDataSetChanged();
        }
    }



}
