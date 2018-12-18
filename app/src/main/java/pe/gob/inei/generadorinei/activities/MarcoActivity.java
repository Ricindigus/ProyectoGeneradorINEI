package pe.gob.inei.generadorinei.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.MarcoAdapter;
import pe.gob.inei.generadorinei.model.CamposMarco;
import pe.gob.inei.generadorinei.model.DAOEncuesta;
import pe.gob.inei.generadorinei.model.FiltrosMarco;
import pe.gob.inei.generadorinei.model.ItemMarco;


public class MarcoActivity extends AppCompatActivity {
    TextView tvCabecera1,tvCabecera2,tvCabecera3,tvCabecera4,
            tvCabecera5,tvCabecera6,tvCabecera7;
    TextView tvFiltro1,tvFiltro2,tvFiltro3,tvFiltro4;
    Spinner spFiltro1, spFiltro2, spFiltro3, spFiltro4;
    LinearLayout lytFiltro1,lytFiltro2,lytFiltro3,lytFiltro4;
    Toolbar toolbar;
    RecyclerView recyclerView;
    DAOEncuesta daoEncuesta;
    CamposMarco camposMarco;
    FiltrosMarco filtrosMarco;
    MarcoAdapter marcoAdapter;
    ArrayList<ItemMarco> marcos;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marco);
        daoEncuesta = new DAOEncuesta(this);
        idUsuario = getIntent().getExtras().getString("idUsuario");
        conectarVistas();
        configurarToolbar();
        configurarFiltros();
        configurarCabeceraMarco();
        configurarRecycler();
    }

    public void conectarVistas(){
        tvCabecera1 = findViewById(R.id.tvCabeceraMarco1);
        tvCabecera2 = findViewById(R.id.tvCabeceraMarco2);
        tvCabecera3 = findViewById(R.id.tvCabeceraMarco3);
        tvCabecera4 = findViewById(R.id.tvCabeceraMarco4);
        tvCabecera5 = findViewById(R.id.tvCabeceraMarco5);
        tvCabecera6 = findViewById(R.id.tvCabeceraMarco6);
        tvCabecera7 = findViewById(R.id.tvCabeceraMarco7);
        tvFiltro1 = (findViewById(R.id.filtro1)).findViewById(R.id.tvNombreFiltro);
        tvFiltro2 = (findViewById(R.id.filtro2)).findViewById(R.id.tvNombreFiltro);
        tvFiltro3 = (findViewById(R.id.filtro3)).findViewById(R.id.tvNombreFiltro);
        tvFiltro4 = (findViewById(R.id.filtro4)).findViewById(R.id.tvNombreFiltro);
        spFiltro1 = (findViewById(R.id.filtro1)).findViewById(R.id.spFiltro);
        spFiltro2 = (findViewById(R.id.filtro2)).findViewById(R.id.spFiltro);
        spFiltro3 = (findViewById(R.id.filtro3)).findViewById(R.id.spFiltro);
        spFiltro4 = (findViewById(R.id.filtro4)).findViewById(R.id.spFiltro);
        toolbar = findViewById(R.id.marco_toolbar);
        recyclerView = findViewById(R.id.rvItemsMarco);
    }

    private void configurarToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(daoEncuesta.getEncuesta().getTitulo());
    }
    private void configurarFiltros() {
        filtrosMarco = daoEncuesta.getFiltrosMarco();
        configurarFiltroSpinner(filtrosMarco.getNombre1(),spFiltro1,tvFiltro1);

        configurarFiltroSpinner(filtrosMarco.getNombre2(),spFiltro2,tvFiltro2);

        configurarFiltroSpinner(filtrosMarco.getNombre3(),spFiltro3,tvFiltro3);
        configurarFiltroSpinner(filtrosMarco.getNombre4(),spFiltro4,tvFiltro4);
    }

    private void configurarFiltroSpinner(String nombreFiltro, Spinner spinner,TextView textView) {
        if (nombreFiltro.equals("")) spinner.setVisibility(View.GONE);
        else textView.setText(nombreFiltro);
    }

    private void configurarCabeceraMarco() {
        camposMarco = daoEncuesta.getCamposMarco();
        configurarCampo(camposMarco.getNombre1(),camposMarco.getPeso1(),tvCabecera1);
        configurarCampo(camposMarco.getNombre2(),camposMarco.getPeso2(),tvCabecera2);
        configurarCampo(camposMarco.getNombre3(),camposMarco.getPeso3(),tvCabecera3);
        configurarCampo(camposMarco.getNombre4(),camposMarco.getPeso4(),tvCabecera4);
        configurarCampo(camposMarco.getNombre5(),camposMarco.getPeso5(),tvCabecera5);
        configurarCampo(camposMarco.getNombre6(),camposMarco.getPeso6(),tvCabecera6);
        configurarCampo(camposMarco.getNombre7(),camposMarco.getPeso7(),tvCabecera7);
    }

    private void configurarCampo(String nombreCampo, String peso, TextView textView) {
        if (nombreCampo.equals(""))
            textView.setVisibility(View.GONE);
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, Integer.parseInt(peso)*1.0f);
            textView.setLayoutParams(params);
            textView.setText(nombreCampo);
        }
    }

    private void configurarRecycler(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marcoAdapter = new MarcoAdapter(daoEncuesta.getCamposMarco(), new MarcoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(marcoAdapter);
        marcos = daoEncuesta.getAllItemsMarco(idUsuario,daoEncuesta.getCamposMarco());
        marcoAdapter.setMarcos(marcos);
    }


}
