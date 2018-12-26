package pe.gob.inei.generadorinei.activities.empresas;

import android.annotation.SuppressLint;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.ExpandListAdapter;
import pe.gob.inei.generadorinei.model.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.Modulo;
import pe.gob.inei.generadorinei.model.pojos.Pagina;
import pe.gob.inei.generadorinei.util.NombreSeccionFragment;
import pe.gob.inei.generadorinei.util.TipoActividad;

public class EmpresaActivity extends AppCompatActivity {
    ExpandableListView expListView;
    Button btnAtras, btnSiguiente;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandListAdapter listAdapter;
    DAOEncuesta daoEncuesta;

    String tituloEncuesta;
    String usuario = "ENC0001";
    String encuestado = "1266";
    ArrayList<Modulo> modulos;

    LinearLayout layoutScrolleable, lytComponente1,
            lytComponente2, lytComponente3, lytComponente4,
            lytComponente5, lytComponente6, lytComponente7,
            lytComponente8, lytComponente9, lytComponente10;
    View lytFocus;

    String idModuloActual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        daoEncuesta = new DAOEncuesta(this);
        tituloEncuesta = daoEncuesta.getEncuesta().getTitulo();
        conectarVistas();
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        configurarCabeceraNavigation();
        configurarListaExpandible();
    }

    private void configurarCabeceraNavigation() {
        View headerView = navigationView.getHeaderView(0);
        TextView txtHeaderTitulo = (TextView) headerView.findViewById(R.id.header_txtTitulo);
        TextView txtHeaderEncuestado = (TextView) headerView.findViewById(R.id.header_txtEncuestado);
        TextView txtHeaderUsuario = (TextView) headerView.findViewById(R.id.header_txtUsuario);
        txtHeaderTitulo.setText(tituloEncuesta);
        txtHeaderEncuestado.setText("Empresa " + encuestado);
        txtHeaderUsuario.setText("Usuario: " + usuario);
    }

    private void conectarVistas() {
        //Barra y Drawer
        drawerLayout = findViewById(R.id.drawer_encuesta_layout);
        toolbar = findViewById(R.id.my_toolbar);
        //Botones avanzar y retorceder
        btnAtras = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        //navigation y lista expandible
        navigationView = findViewById(R.id.navigation_view);
        expListView = findViewById(R.id.lista_expandible_navigation);
        //Layouts: scrolleable para componente visitas
        //focus para funcinamiento auxiliar
        layoutScrolleable = findViewById(R.id.layout_componente_scrolleable);
        lytFocus =  findViewById(R.id.layout_focus);
        //layouts componentes
        lytComponente1 = findViewById(R.id.layout_componente1);
        lytComponente2 = findViewById(R.id.layout_componente2);
        lytComponente3 = findViewById(R.id.layout_componente3);
        lytComponente4 = findViewById(R.id.layout_componente4);
        lytComponente5 = findViewById(R.id.layout_componente5);
        lytComponente6 = findViewById(R.id.layout_componente6);
        lytComponente7 = findViewById(R.id.layout_componente7);
        lytComponente8 = findViewById(R.id.layout_componente8);
        lytComponente9 = findViewById(R.id.layout_componente9);
        lytComponente10 = findViewById(R.id.layout_componente10);
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

    public void setNombreSeccion(int nPagina, int direccion) {
        String nombreSeccion = "";
        int numeroDePagina = nPagina;
        String idModulo = daoEncuesta.getPagina(numeroDePagina + "",TipoActividad.ACTIVIDAD_EMPRESA).getModulo();
        if (!idModulo.equals(idModuloActual)) {
            nombreSeccion = daoEncuesta.getModulo(idModulo,TipoActividad.ACTIVIDAD_EMPRESA).getTitulo();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (direccion > 0) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            }
            NombreSeccionFragment nombreSeccionFragment = new NombreSeccionFragment(nombreSeccion);
            fragmentTransaction.replace(R.id.lytNombreModulo, nombreSeccionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            idModuloActual = idModulo;
        }
    }

}
