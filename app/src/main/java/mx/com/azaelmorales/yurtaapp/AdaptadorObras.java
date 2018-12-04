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

public class AdaptadorObras extends BaseAdapter  implements Filterable{
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<Obra> obras;
    CustomFilter filter;
    ArrayList<Obra> filterList;

    public AdaptadorObras(Context context,ArrayList<Obra> obras){
        this.context = context;
        this.obras = obras;
        this.filterList = obras;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.elemento_obra,null);
        TextView textViewID = vista.findViewById(R.id.textViewIDObra);
        TextView textViewNombre = vista.findViewById(R.id.textViewNombreObra);
        TextView textViewLugar = vista.findViewById(R.id.textViewLugarObra);
        textViewID.setText("ID: "+ obras.get(i).getId());
        textViewNombre.setText("Nombre:"+ obras.get(i).getNombre());
        textViewLugar.setText("Lugar:"+ obras.get(i).getLugar());
        return vista;
    }
    @Override
    public int getCount() {
        return obras.size();
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
                ArrayList<Obra> filters = new ArrayList<Obra>();
                for (int i=0; i<filterList.size(); i++){
                    if(filterList.get(i).getLugar().toUpperCase().contains(constraint)){
                        Obra o = new Obra(filterList.get(i).getId(),filterList.get(i).getNombre(),
                                filterList.get(i).getLugar());
                        filters.add(o);
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
            obras = (ArrayList<Obra>) results.values;
            notifyDataSetChanged();
        }
    }

}
