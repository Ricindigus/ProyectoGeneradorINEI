package pe.gob.inei.generadorinei.activities.empresas;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.ExpandListAdapter;
import pe.gob.inei.generadorinei.model.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.Modulo;
import pe.gob.inei.generadorinei.model.pojos.Pagina;
import pe.gob.inei.generadorinei.util.TipoActividad;

public class EmpresaActivity extends AppCompatActivity {
    ExpandableListView expListView;
    Button btnAtras;
    Button btnSiguiente;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandListAdapter listAdapter;
    DAOEncuesta daoEncuesta;
    String tituloEmpresa;
    String usuario;
    String encuestado;
    ArrayList<Modulo> modulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        daoEncuesta = new DAOEncuesta(this);
        conectarVistas();
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        configurarListaExpandible();
    }



    private void conectarVistas() {
        drawerLayout = findViewById(R.id.drawer_encuesta_layout);
        toolbar = findViewById(R.id.my_toolbar);
        btnAtras = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        expListView = findViewById(R.id.lista_expandible_navigation);
        navigationView = findViewById(R.id.navigation_view);
    }

    private void configurarListaExpandible() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                ArrayList<Modulo> modulos = daoEncuesta.getAllModulos(TipoActividad.ACTIVIDAD_EMPRESA);
//                Modulo modulo = modulos.get(groupPosition);
//                ArrayList<Pagina> paginas = dataComp.getPaginasxModulo(modulo.getID());
//                int numPagina = Integer.parseInt(paginas.get(childPosition).get_id());
//                if (numPagina < posicionFragment) setNombreSeccion(numPagina, -1);
//                else setNombreSeccion(numPagina, 1);
//                setPagina(numPagina, 1);
//                posicionFragment = numPagina;
//                dataComp.close();
                return false;
            }
        });
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {

        ArrayList<Modulo> modulos = daoEncuesta.getAllModulos(TipoActividad.ACTIVIDAD_EMPRESA);
        for (Modulo moduloActual : modulos) {
            //pone la cabecera
            listDataHeader.add(moduloActual.getCabecera());
            ArrayList<Pagina> paginas = daoEncuesta.getPaginasxModulo(moduloActual.get_id());
            List<String> subItems = new ArrayList<String>();
            //busca los subtitulos
            for (Pagina paginaActual : paginas) {
                subItems.add(paginaActual.getNombre());
            }
            //agrega cabecera y subtitulos
            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), subItems);
        }
    }

}
