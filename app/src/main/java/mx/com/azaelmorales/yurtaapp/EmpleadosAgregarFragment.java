package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class EmpleadosAgregarFragment extends Fragment implements  View.OnClickListener{
    private EditText txt_empleado_date;
    private AppCompatSpinner spinner_sexo;
    List<String> listaGeneros;
    ArrayAdapter<String> adapterSpinner;
    private int dia,mes,anio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_empleados_agregar, container, false);

        spinner_sexo = (AppCompatSpinner)view.findViewById(R.id.spinner_sexo);
        listaGeneros = new ArrayList<>();
        String[] generos = {"Masculino","Femenino"};
        Collections.addAll(listaGeneros, generos);

        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listaGeneros);

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_sexo.setAdapter(adapterSpinner);
        spinner_sexo.setSelection(0);



        txt_empleado_date = (EditText) view.findViewById(R.id.txt_empleado_date);
        txt_empleado_date.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==txt_empleado_date){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int i, int i1, int i2) {
                    txt_empleado_date.setText(i2 +"/"+(i1+1)+"/"+i);
                }
            },anio,mes,dia);
            datePickerDialog.show();
        }
    }


}
