package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.interfaces.ActividadInterfaz;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.util.ComponenteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends ComponenteFragment {

    private String idEmpresa;
    private PRadio pRadio;
    private ArrayList<SPRadio> subpreguntas;
    private Context  context;
    private TextView txtPregunta;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15;
    private EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9, edit10, edit11, edit12, edit13, edit14, edit15;
    private RadioButton[] radioButtons;
    private EditText[] editTexts;
    private View rootView;
    private int[] idRbs = {R.id.radio_sp1, R.id.radio_sp2, R.id.radio_sp3, R.id.radio_sp4, R.id.radio_sp5, R.id.radio_sp6,
            R.id.radio_sp7, R.id.radio_sp8, R.id.radio_sp9, R.id.radio_sp10, R.id.radio_sp11, R.id.radio_sp12, R.id.radio_sp13,
            R.id.radio_sp14, R.id.radio_sp15};
    private int[] idEdits = {R.id.radio_descripcion1, R.id.radio_descripcion2, R.id.radio_descripcion3, R.id.radio_descripcion4, R.id.radio_descripcion5,
            R.id.radio_descripcion6, R.id.radio_descripcion7, R.id.radio_descripcion8, R.id.radio_descripcion9, R.id.radio_descripcion10, R.id.radio_descripcion11,
            R.id.radio_descripcion12, R.id.radio_descripcion13, R.id.radio_descripcion14, R.id.radio_descripcion15};
    private boolean cargandoDatos = false;
    public RadioFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RadioFragment(PRadio pRadio, ArrayList<SPRadio> subpreguntas, Context context, String idEmpresa) {
        this.pRadio = pRadio;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.idEmpresa = idEmpresa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_radio, container, false);
        txtPregunta = (TextView) rootView.findViewById(R.id.radio_pregunta);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_grupo);

        radioButtons = new RadioButton[]{rb1,rb2,rb3,rb4, rb5,rb6,rb7,rb8,rb9,rb10,rb11,rb12,rb13,rb14,rb15};
        editTexts = new EditText[]{edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10,edit11,edit12,edit13,edit14,edit15};

        for (int i = 0; i <subpreguntas.size() ; i++) {
            radioButtons[i] = (RadioButton) rootView.findViewById(idRbs[i]);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            editTexts[i] = (EditText) rootView.findViewById(idEdits[i]);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        cargarDatos();
    }

    @Override
    public void llenarVista(){
        txtPregunta.setText(pRadio.getNumero() + ". " + pRadio.getPregunta().toUpperCase());
        for (int i = 0; i <subpreguntas.size() ; i++) {
            final SPRadio spRadio = subpreguntas.get(i);
            final RadioButton radioButton = radioButtons[i];
            final EditText editText = editTexts[i];
            radioButton.setVisibility(View.VISIBLE);

            radioButton.setText(spRadio.getSubpregunta());
            if(!spRadio.getVar_especifique().equals("")){
                editText.setVisibility(View.VISIBLE);
                editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            ocultarTeclado(editText);
                            radioGroup.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
            }


            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!spRadio.getVar_especifique().equals("")){
                        if(isChecked){
                            editText.setEnabled(true);
                            editText.setBackgroundResource(R.drawable.edittext_enabled);
                        } else{
                            editText.setText("");
                            editText.setEnabled(false);
                            editText.setBackgroundResource(R.drawable.edittext_disabled);
                        }
                    }
                }
            });
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = group.findViewById(checkedId);
//                int posicion = group.indexOfChild(radioButton)/2;
//                ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
//                if(actividadInterfaz.existeEvento(pRadio.get_id())){
//                    actividadInterfaz.realizarEvento(pRadio.get_id(),posicion + "",cargandoDatos);
//                }
            }
        });
    }

    @Override
    public void cargarDatos(){
//        Data d = new Data(context);
//        d.open();
//        if(d.getNumeroControladores(idEmpresa,pRadio.getID()) == 0){
//            cargandoDatos = true;
//            DataTablas data = new DataTablas(context);
//            data.open();
//            String valorCheck;
//            String valorEspecifique;
//            if(data.existenDatos(getIdTabla(),idEmpresa)){
//                valorCheck = data.getValor(getIdTabla(),subpreguntas.get(1).getVARIABLE(),idEmpresa);
//                if(valorCheck != null ){
//                    if (!valorCheck.equals("")){
//                        int childPos = Integer.parseInt(valorCheck);
//                        if(childPos != -1) ((RadioButton) radioGroup.getChildAt(childPos*2)).setChecked(true);
//                    }
//                }
//                for (int i = 0; i < subpreguntas.size() ; i++) {
//                    if(!subpreguntas.get(i).getVARDESC().equals("")){
//                        valorEspecifique = data.getValor(getIdTabla(),subpreguntas.get(i).getVARDESC(),idEmpresa);
//                        if(valorEspecifique != null)editTexts[i].setText(valorEspecifique);
//                    }
//                }
//            }
//            data.close();
//            cargandoDatos = false;
//        }else{
//            inhabilitar();
//        }

    }

    @Override
    public void guardarDatos(){
//        DataTablas data = new DataTablas(context);
//        data.open();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("ID_EMPRESA",idEmpresa);
//        if(radioGroup.getCheckedRadioButtonId() == -1) contentValues.put(subpreguntas.get(1).getVARIABLE(),"-1");
//        else contentValues.put(subpreguntas.get(1).getVARIABLE(),radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))/2);
//        for (int i = 0; i < subpreguntas.size(); i++) {
//            if(!subpreguntas.get(i).getVARDESC().equals(""))contentValues.put(subpreguntas.get(i).getVARDESC(),editTexts[i].getText().toString());
//        }
//        if(!data.existenDatos(getIdTabla(),idEmpresa))data.insertarValores(getIdTabla(),contentValues);
//        else data.actualizarValores(getIdTabla(),idEmpresa,contentValues);
//        data.close();
    }

    @Override
    public boolean validarDatos(){
        boolean correcto = true;
//        String mensaje = "";
//        if(estaHabilitado()){
//            if(radioGroup.getCheckedRadioButtonId() == -1){
//                correcto = false;
//                if(mensaje.equals("")) mensaje = "PREGUNTA " + pRadio.getNUMERO() + ": DEBE SELECCIONAR UNA OPCION";
//            }else{
//                for (int i = 0; i <subpreguntas.size() ; i++) {
//                    if(editTexts[i].isEnabled() && !subpreguntas.get(i).getVARDESC().equals("")){
//                        String campo = editTexts[i].getText().toString().trim();
//                        if(campo.equals("")){
//                            correcto = false;
//                            if(mensaje.equals("")) mensaje = "PREGUNTA " + pRadio.getNUMERO() + ": DEBE ESPECIFICAR";
//                        }
//                    }
//                }
//            }
//        }
//        if(!correcto){
//            mostrarMensaje(mensaje);
//        }
        return correcto;
    }

    @Override
    public void inhabilitar(){
        radioGroup.clearCheck();
        rootView.setVisibility(View.GONE);
    }

    @Override
    public void habilitar() {
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
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

    @Override
    public String getIdTabla(){
        return pRadio.getId_tabla();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
