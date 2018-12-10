package mx.com.azaelmorales.yurtaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMaterialSolicitado extends BaseAdapter implements Filterable {
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<Material> materiales;
    static ArrayList<Material> filterList;
    static CustomFilterMaterial filter;
    static  ArrayList<Material> listaFiltrada;

    public AdapterMaterialSolicitado(Context context, ArrayList<Material> materiales){
        this.context = context;
        this.materiales = materiales;
        this.filterList = materiales;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.element_material,null);
        TextView textViewNombre = vista.findViewById(R.id.textViewnombremat);
        TextView textViewCorreo = vista.findViewById(R.id.textViewmarca);
        TextView textViewRfc = vista.findViewById(R.id.textViewExistencias);


        textViewNombre.setText("Codigo: "+ materiales.get(i).getCodigo());
        textViewRfc.setText("Nombre: "+ materiales.get(i).getNombre());
        textViewCorreo.setText("Cantidad: "+ materiales.get(i).getCantidadSolicitada());
        return vista;
    }


    @Override
    public int getCount() {
        return materiales.size();
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
            filter= new CustomFilterMaterial();
        }
        return  filter;
    }


    class CustomFilterMaterial extends  Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Material> filters = new ArrayList<Material>();
                for (int i=0; i<filterList.size(); i++){
                    if(filterList.get(i).getNombre().toUpperCase().contains(constraint)){
                        Material m = new Material(filterList.get(i).getNombre(),filterList.get(i).getMarca(),
                                filterList.get(i).getExistecias());
                        filters.add(m);
                    }
                }
                results.count = filters.size();
                results.values= filters;
                listaFiltrada = filters;

            }else{
                results.count = filterList.size();
                results.values= filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            materiales = (ArrayList<Material>) results.values;
            notifyDataSetChanged();
        }
    }
}