package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PUbicacion;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UbicacionFragment extends ComponenteFragment {

    private View rootView;
    private Spinner spDepartamentos;
    private Spinner spProvincias;
    private Spinner spDistritos;
    private Context contexto;
    private String idEmpresa;
    private DAOEncuesta daoEncuesta;
    private PUbicacion pUbicacion;
    private ArrayList<String> departamentos;

    public UbicacionFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public UbicacionFragment(Context contexto, String idEmpresa, PUbicacion pUbicacion) {
        this.contexto = contexto;
        this.idEmpresa = idEmpresa;
        this.pUbicacion = pUbicacion;
        daoEncuesta = new DAOEncuesta(contexto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        spDepartamentos = (Spinner) rootView.findViewById(R.id.ubicacion_sp_departamento);
        spProvincias = (Spinner) rootView.findViewById(R.id.ubicacion_sp_provincia);
        spDistritos = (Spinner) rootView.findViewById(R.id.ubicacion_sp_distrito);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        spDepartamentos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    spDepartamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, final int pos, long l) {
                            ArrayList<String> provincias = new ArrayList<>();
                            if(pos!=0) provincias = daoEncuesta.getArrayProvincias(spDepartamentos.getSelectedItem().toString());
                            cargarSpinerProvincias(provincias);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });

                    spProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, final int pos, long l) {
                            ArrayList<String> distritos = new ArrayList<>();
                            if(pos != 0) distritos = daoEncuesta.getArrayDistritos(spDepartamentos.getSelectedItem().toString(),
                                    spProvincias.getSelectedItem().toString());
                            cargarSpinerDistritos(distritos);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                }
                return false;
            }
        });


        spProvincias.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    spProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, final int pos, long l) {
                            ArrayList<String> distritos = new ArrayList<>();
                            if(pos != 0) distritos = daoEncuesta.getArrayDistritos(spDepartamentos.getSelectedItem().toString(),
                                    spProvincias.getSelectedItem().toString());
                            cargarSpinerDistritos(distritos);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                }
                return false;
            }
        });
        cargarDatos();
    }

    public void cargarDatos(){
        departamentos = daoEncuesta.getArrayDepartamentos();
        cargarSpinerDepartamentos(departamentos);
//        dataTablas = new DataTablas(contexto);
//        dataTablas.open();
//        boolean existenDatos = dataTablas.existenDatos(getIdTabla(),idEmpresa);
//        dataTablas.close();
//        if(existenDatos){
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    final String[] departamentos = contexto.getResources().getStringArray(R.array.departamentos);
//                    data = new Data(contexto);
//                    dataTablas = new DataTablas(contexto);
//                    data.open();
//                    dataTablas.open();
//                    final String codDep = dataTablas.getValor(getIdTabla(),ubicacion.getVARDEP(),idEmpresa);
//                    final String codProv = dataTablas.getValor(getIdTabla(),ubicacion.getVARPRO(),idEmpresa);
//                    final String codDis = dataTablas.getValor(getIdTabla(),ubicacion.getVARDIS(),idEmpresa);
//                    final String[] provincias = contexto.getResources().getStringArray(arregloDepartamentos[Integer.parseInt(codDep)-1]);
//                    final ArrayList<String> distritos = data.getUbigeos(checkDigito(Integer.parseInt(codDep))+ checkDigito(Integer.parseInt(codProv)));
//                    boolean encontrado = false;
//                    int i = 1;
//                    while(!encontrado && i < distritos.size()){
//                        if(checkDigito(Integer.parseInt(codDis)).equals(obtenerCodigo(distritos.get(i)))) encontrado=true;
//                        i++;
//                    }
//                    final int codigoSpinerDistrito = i-1;
//                    data.close();
//                    dataTablas.close();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            cargarSpinerDepartamentos(departamentos);
//                            cargarSpinerProvincias(provincias);
//                            cargarSpinerDistritos(distritos);
//                            spDepartamentos.setSelection(Integer.parseInt(codDep));
//                            spProvincias.setSelection(Integer.parseInt(codProv));
//                            spDistritos.setSelection(codigoSpinerDistrito);
//                        }
//                    });
//                }
//            };
//            thread.start();
//        }else{
//            cargarSpinerDepartamentos(contexto.getResources().getStringArray(R.array.departamentos));
//            cargarSpinerProvincias(new ArrayList<String>());
//            cargarSpinerDistritos(new ArrayList<String>());
//        }
    }
    public boolean validarDatos(){
        boolean correcto = true;
//        String mensaje = "";
//        if(spDepartamentos.getSelectedItemPosition() < 1 ||
//                spProvincias.getSelectedItemPosition() < 1 ||
//                spDistritos.getSelectedItemPosition() < 1){
//            correcto = false;
//            mensaje = "Debe indicar departamento, provincia y distrito";
//        }
//        if(!correcto) mostrarMensaje(mensaje);
        return correcto;
    }
    public void guardarDatos(){
//        DataTablas dataTables = new DataTablas(contexto);
//        dataTables.open();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ubicacion.getVARDEP(), Integer.parseInt(obtenerCodigo(spDepartamentos.getSelectedItem().toString())));
//        contentValues.put(ubicacion.getVARPRO(), Integer.parseInt(obtenerCodigo(spProvincias.getSelectedItem().toString())));
//        contentValues.put(ubicacion.getVARDIS(), Integer.parseInt(obtenerCodigo(spDistritos.getSelectedItem().toString())));
//        if(!dataTables.existenDatos(getIdTabla(),idEmpresa)){
//            contentValues.put("ID_EMPRESA",idEmpresa);
//            dataTables.insertarValores(getIdTabla(),contentValues);
//        }else dataTables.actualizarValores(getIdTabla(),idEmpresa,contentValues);
//        dataTables.close();
    }

    public void llenarVista(){}


    public String obtenerCodigo(String opcion){
        String codigo = "";
        codigo = opcion.substring(opcion.indexOf("[")+1,opcion.indexOf("]"));
        return codigo;
    }




    public void cargarSpinerDepartamentos(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartamentos.setAdapter(adapter);
    }

    public void cargarSpinerProvincias(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincias.setAdapter(adapter);
    }

    public void cargarSpinerDistritos(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistritos.setAdapter(adapter);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void inhabilitar(){
        rootView.setVisibility(View.GONE);
    }

    @Override
    public void habilitar() {
        rootView.setVisibility(View.VISIBLE);
    }

    public boolean estaHabilitado(){
        boolean habilitado = false;
        if(rootView.getVisibility() == View.VISIBLE) habilitado = true;
        return habilitado;
    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public String getIdTabla(){
        return pUbicacion.getId_tabla();
    }
}
