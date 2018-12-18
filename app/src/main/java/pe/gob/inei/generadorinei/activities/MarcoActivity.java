package pe.gob.inei.generadorinei.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pe.gob.inei.generadorinei.R;



public class MarcoActivity extends AppCompatActivity {
    TextView tvCabecera1,tvCabecera2,tvCabecera3,tvCabecera4,
            tvCabecera5,tvCabecera6,tvCabecera7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marco);
        conectarVistas();


    }

    public void conectarVistas(){
        tvCabecera1 = findViewById(R.id.tvCabeceraMarco1);
        tvCabecera2 = findViewById(R.id.tvCabeceraMarco2);
        tvCabecera3 = findViewById(R.id.tvCabeceraMarco3);
        tvCabecera4 = findViewById(R.id.tvCabeceraMarco4);
        tvCabecera5 = findViewById(R.id.tvCabeceraMarco5);
        tvCabecera6 = findViewById(R.id.tvCabeceraMarco6);
        tvCabecera7 = findViewById(R.id.tvCabeceraMarco7);


    }
}
