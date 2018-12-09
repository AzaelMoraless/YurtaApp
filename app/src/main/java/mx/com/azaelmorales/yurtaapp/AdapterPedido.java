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

public class AdapterPedido extends BaseAdapter implements Filterable {
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<Pedido> pedidos;
    static ArrayList<Pedido> filterList;
    static CustomFilterPedido filter;

    public AdapterPedido(Context context, ArrayList<Pedido> pedidos){
        this.context = context;
        this.pedidos = pedidos;
        this.filterList = pedidos;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vista = inflater.inflate(R.layout.elemento_pedido,null);

        TextView textViewFolio = vista.findViewById(R.id.textViewFolioP);
        TextView textViewFecha = vista.findViewById(R.id.textViewFechaP);
        TextView textViewEstado = vista.findViewById(R.id.textViewEstadoP);
        textViewFolio.setText("Folio: "+ pedidos.get(i).getFolioPedido());
        textViewFecha.setText("Fecha: "+ pedidos.get(i).getFecha());
        textViewEstado.setText("Estado: "+ pedidos.get(i).getEstado());
        return vista;
    }


    @Override
    public int getCount() {
        return pedidos.size();
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
            filter= new CustomFilterPedido();
        }
        return  filter;
    }


    class CustomFilterPedido extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Pedido> filters = new ArrayList<Pedido>();
                for (int i=0; i<filterList.size(); i++){
                    if(filterList.get(i).getFolioPedido().toUpperCase().contains(constraint)){
                        Pedido p = new Pedido(filterList.get(i).getFolioPedido(),filterList.get(i).getFecha(),
                                filterList.get(i).getEstado());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values= filters;
                ///listaFiltrada = filters;

            }else{
                results.count = filterList.size();
                results.values= filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            pedidos = (ArrayList<Pedido>) results.values;
            notifyDataSetChanged();
        }
    }
}