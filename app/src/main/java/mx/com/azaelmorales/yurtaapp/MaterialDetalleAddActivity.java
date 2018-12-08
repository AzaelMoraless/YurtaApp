package mx.com.azaelmorales.yurtaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MaterialDetalleAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextCantidad;
    private Button buttonAdd,buttonSub;
    private int cantidad=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detalle_add);


    }

    @Override
    public void onClick(View view) {


    }
}
