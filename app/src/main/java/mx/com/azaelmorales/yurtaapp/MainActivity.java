package mx.com.azaelmorales.yurtaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btn_start);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
    }

    public void clickStart(View v){
        Intent home_activity = new Intent(getApplicationContext(),HomeActivity.class);
        finish();

        startActivity(home_activity);
    }
}
