package mx.com.azaelmorales.yurtaapp;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;

public class ObraAgregarFragment extends Fragment implements  View.OnClickListener {
    private EditText editTextFolio,editTextNombre,editTextDependencia,editTextLugar,
                        editTextFechaInicio;

    private TextInputLayout textInputLayoutNombre,textInputLayoutDependencia,textInputLayoutLugar,
                            textInputLayoutFecha;
    private AppCompatSpinner spinnerTipo;
    List<String> listaTipos;
    ArrayAdapter<String> adapterSpinnerTipos;
    private int dia,mes,anio;
    private String fehca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_obra_agregar, container, false);

        editTextFolio = (EditText)view.findViewById(R.id.et_folio_obra);
        editTextNombre =(EditText)view.findViewById(R.id.et_nombre_obra);
        editTextDependencia =(EditText)view.findViewById(R.id.et_dependencia_obra);
        editTextLugar=(EditText)view.findViewById(R.id.et_lugar_obra);
        editTextFechaInicio =(EditText)view.findViewById(R.id.et_fecha_obra);

        textInputLayoutNombre =(TextInputLayout)view.findViewById(R.id.til_nombre_obra);
        textInputLayoutDependencia =(TextInputLayout)view.findViewById(R.id.til_dependencia_obra);
        textInputLayoutLugar =(TextInputLayout)view.findViewById(R.id.til_lugar_obra);
        textInputLayoutFecha =(TextInputLayout)view.findViewById(R.id.til_fecha_obra);

        spinnerTipo = (AppCompatSpinner)view.findViewById(R.id.spn_tipo_obra);
        listaTipos = new ArrayList<>();
        String[] tipos = {"Pavimentacion","Techado","Agua potable"};
        Collections.addAll(listaTipos, tipos);
        adapterSpinnerTipos = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listaTipos);
        adapterSpinnerTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterSpinnerTipos);
        spinnerTipo.setSelection(0);

        editTextFechaInicio.setOnClickListener(this);


        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_modificar, menu);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_aceptar:

                return true;
            case R.id.nav_cancelar:
                getFragmentManager().beginTransaction().remove(this).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==editTextFechaInicio){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int i, int i1, int i2) {
                    fehca =  i +"-"+(i1+1)+"-"+i2;
                    editTextFechaInicio.setText(fehca);
                }
            },anio,mes,dia);
            datePickerDialog.show();
        }
    }
}
