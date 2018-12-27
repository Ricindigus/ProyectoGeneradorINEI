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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.interfaces.ActividadInterfaz;
import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPCheckbox;
import pe.gob.inei.generadorinei.util.ComponenteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckBoxFragment extends ComponenteFragment {

    private Context context;
    private PCheckbox pCheckBox;
    private ArrayList<SPCheckbox> subpreguntas;
    private LinearLayout lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10, lyt11,lyt12,lyt13,lyt14,lyt15,lyt16,lyt17,lyt18,lyt19,lyt20;
    private CheckBox ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9,ck10, ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20;
    private EditText edt1,edt2,edt3,edt4,edt5,edt6,edt7,edt8,edt9,edt10, edt11,edt12,edt13,edt14,edt15,edt16,edt17,edt18,edt19,edt20;
    private TextView txtPregunta;
    private LinearLayout[] checkLayouts;
    private CheckBox[] checkBoxes;
    private EditText[] editTexts;
    private View rootView;
    private String idEmpresa;
    int[] idLinear = {R.id.checkbox_sp1, R.id.checkbox_sp2, R.id.checkbox_sp3, R.id.checkbox_sp4, R.id.checkbox_sp5, R.id.checkbox_sp6, R.id.checkbox_sp7,
            R.id.checkbox_sp8, R.id.checkbox_sp9, R.id.checkbox_sp10, R.id.checkbox_sp11, R.id.checkbox_sp12, R.id.checkbox_sp13, R.id.checkbox_sp14,
            R.id.checkbox_sp15, R.id.checkbox_sp16, R.id.checkbox_sp17, R.id.checkbox_sp18, R.id.checkbox_sp19, R.id.checkbox_sp20};
    private boolean cargandoDatos = false;

    public CheckBoxFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CheckBoxFragment(PCheckbox pCheckBox, ArrayList<SPCheckbox> subpreguntas, Context context, String idEmpresa) {
        this.context = context;
        this.pCheckBox = pCheckBox;
        this.subpreguntas = subpreguntas;
        this.idEmpresa = idEmpresa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_check_box, container, false);
        txtPregunta = (TextView) rootView.findViewById(R.id.checkbox_pregunta);
        checkLayouts = new LinearLayout[]{lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10, lyt11,lyt12,lyt13,lyt14,lyt15,lyt16,lyt17,lyt18,lyt19,lyt20};
        checkBoxes = new CheckBox[]{ck1, ck2, ck3, ck4, ck5, ck6, ck7, ck8, ck9, ck10, ck11, ck12, ck13, ck14, ck15, ck16, ck17, ck18, ck19,ck20};
        editTexts = new EditText[]{edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8, edt9, edt10, edt11, edt12, edt13, edt14, edt15, edt16, edt17, edt18, edt19,edt20};

        for (int i = 0; i <subpreguntas.size() ; i++) {
            checkLayouts[i] = (LinearLayout)rootView.findViewById(idLinear[i]);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            checkBoxes[i] = (CheckBox) checkLayouts[i].findViewById(R.id.checkbox_check);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            editTexts[i] = (EditText) checkLayouts[i].findViewById(R.id.checkbox_descripcion);
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
        txtPregunta.setText(pCheckBox.getNumero() + ". " + pCheckBox.getPregunta().toUpperCase());
        for (int i = 0; i <subpreguntas.size() ; i++) {
            final int pos = i;
            final LinearLayout linearLayout = checkLayouts[i];
            final CheckBox checkBox = checkBoxes[i];
            final EditText editText = editTexts[i];
            linearLayout.setVisibility(View.VISIBLE);
            final SPCheckbox spCheckBox = subpreguntas.get(i);
            checkBox.setText(spCheckBox.getSubpregunta());
            if(!spCheckBox.getVar_especifique().equals("")){
                editText.setVisibility(View.VISIBLE);
                editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            ocultarTeclado(editText);
                            linearLayout.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!spCheckBox.getVar_especifique().equals("")){
                        if(isChecked){
                            editText.setEnabled(true);
                            editText.setBackgroundResource(R.drawable.edittext_enabled);
                        }else{
                            editText.setText("");
                            editText.setEnabled(false);
                            editText.setBackgroundResource(R.drawable.edittext_disabled);
                        }
                    }

                    if(!spCheckBox.getDeshabilita().equals("")){
                        if(isChecked){
                            for (int j = 0; j < subpreguntas.size(); j++) {
                                if(pos != j){
                                    checkBoxes[j].setChecked(false);
                                    checkBoxes[j].setEnabled(false);
                                }
                            }
                        }else{
                            for (int j = 0; j < subpreguntas.size(); j++) {
                                if(pos != j) checkBoxes[j].setEnabled(true);
                            }
                        }
                    }
                    //AQUI IRAN LOS EVENTOS DE FLUJOS
//                    String valor = "";
//                    if (isChecked) valor = "1";
//                    else valor = "0";
//                    ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
//                    if(actividadInterfaz.existeEvento(spCheckBox.getVar_guardado())){
//                        actividadInterfaz.realizarEvento(spCheckBox.getVar_guardado(),valor,cargandoDatos);
//                    }
                }
            });
        }
    }

    @Override
    public void cargarDatos(){
//        Data d = new Data(context);
//        d.open();
//        if(d.getNumeroControladores(idEmpresa,pCheckBox.getID()) == 0){
//            cargandoDatos = true;
//            DataTablas data = new DataTablas(context);
//            data.open();
//            String valorCheck;
//            String valorEspecifique;
//            if(data.existenDatos(getIdTabla(),idEmpresa)){
//                for (int i = 0; i < subpreguntas.size() ; i++) {
//                    valorCheck = data.getValor(getIdTabla(),subpreguntas.get(i).getVARIABLE(),idEmpresa);
//                    if(valorCheck != null){
//                        if (valorCheck.equals("1")){
//                            checkBoxes[i].setChecked(true);
//                            if(!subpreguntas.get(i).getVARDESC().equals("")){
//                                valorEspecifique = data.getValor(getIdTabla(),subpreguntas.get(i).getVARDESC(),idEmpresa);
//                                if(valorEspecifique != null)editTexts[i].setText(valorEspecifique);
//                            }
//                        }
////                    if (valorCheck.equals("0")) checkBoxes[i].setChecked(false);
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
//        for (int i = 0; i < subpreguntas.size(); i++) {
//            String variable = subpreguntas.get(i).getVARIABLE();
//            if(checkBoxes[i].isChecked()) contentValues.put(variable, "1");
//            else {
//                if(estaHabilitado())contentValues.put(variable, "0");
//                else contentValues.put(variable, "");
//            }
//            if(!subpreguntas.get(i).getVARDESC().equals(""))contentValues.put(subpreguntas.get(i).getVARDESC(),editTexts[i].getText().toString());
//        }
//        if(!data.existenDatos(getIdTabla(),idEmpresa)){
//            //insertar
//            contentValues.put("ID_EMPRESA",idEmpresa);
//            data.insertarValores(getIdTabla(),contentValues);
//        }else data.actualizarValores(getIdTabla(),idEmpresa,contentValues);
//        data.close();
    }

    @Override
    public boolean validarDatos(){
        boolean correcto = false;
//        String mensaje = "";
//        if(estaHabilitado()){
//            for (int c = 0; c <subpreguntas.size() ; c++) {
//                if(checkBoxes[c].isChecked()){
//                    if(!subpreguntas.get(c).getVARDESC().equals("")){
//                        String campo = editTexts[c].getText().toString().trim();
//                        if(!campo.equals("")) correcto = true;
//                        else{
//                            correcto = false;
//                            if(mensaje.equals("")) mensaje = "PREGUNTA " + pCheckBox.getNUMERO() + ": DEBE ESPECIFICAR";
//                        }
//                    }else correcto = true;
//                }
//            }
//        }else correcto = true;
//        if(!correcto){
//            if(mensaje.equals(""))mostrarMensaje("PREGUNTA " + pCheckBox.getNUMERO() + ": DEBE SELECCIONAR UNA OPCION");
//            else mostrarMensaje(mensaje);
//        }
        return correcto;
    }

    @Override
    public void inhabilitar(){
        for (int i = 0; i < subpreguntas.size() ; i++) checkBoxes[i].setChecked(false);
        rootView.setVisibility(View.GONE);
    }

    @Override
    public void habilitar(){
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
        return pCheckBox.getId_tabla();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
