package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPFormulario;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.NumericKeyBoardTransformationMethod;
import pe.gob.inei.generadorinei.util.TipoInput;

/**
 * A simple {@link Fragment} subclass.
 * Ricardo Morales
 */
public class FormularioFragment extends ComponenteFragment {

    private View rootView;
    private DAOEncuesta daoEncuesta;
    private PFormulario pFormulario;
    private ArrayList<SPFormulario> subpreguntas;
    private Context context;
    private String idEmpresa;
    private String idFormulario;
    private TextView formularioTitulo, txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10;
    private CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9,cv10;
    private EditText edtSP1,edtSP2, edtSP3, edtSP4, edtSP5, edtSP6, edtSP7, edtSP8, edtSP9, edtSP10,
                    edtE1,edtE2,edtE3,edtE4,edtE5,edtE6,edtE7,edtE8,edtE9,edtE10;
    private Spinner sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10;
    private CheckBox ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9,ck10;
    private LinearLayout formularioLayout, lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10;
    private CardView[] cardViews;
    private TextView[] textViews;
    private EditText[] editTexts;
    private LinearLayout[] layoutSpinners;
    private Spinner[] spinners;
    private EditText[] edtEspecifiques;
    private CheckBox[] checkBoxes;
    private int[] idCardViews = {R.id.formulario_sp1, R.id.formulario_sp2, R.id.formulario_sp3, R.id.formulario_sp4,
            R.id.formulario_sp5, R.id.formulario_sp6, R.id.formulario_sp7, R.id.formulario_sp8, R.id.formulario_sp9,
            R.id.formulario_sp10};

    public FormularioFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FormularioFragment(PFormulario pFormulario, ArrayList<SPFormulario> subpreguntas, Context context, String idEmpresa) {
        this.pFormulario = pFormulario;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.idEmpresa = idEmpresa;
        daoEncuesta = new DAOEncuesta(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_formulario, container, false);
        formularioTitulo = (TextView) rootView.findViewById(R.id.formulario_titulo);
        cardViews = new CardView[] {cv1, cv2, cv3, cv4, cv5, cv6, cv7, cv8, cv9, cv10};
        textViews = new TextView[]{txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10};
        editTexts = new EditText[]{edtSP1, edtSP2, edtSP3, edtSP4, edtSP5, edtSP6, edtSP7, edtSP8, edtSP9, edtSP10};
        layoutSpinners = new LinearLayout[]{lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10};
        spinners = new Spinner[]{sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10};
        edtEspecifiques = new EditText[]{edtE1,edtE2,edtE3,edtE4,edtE5,edtE6,edtE7,edtE8,edtE9,edtE10};
        checkBoxes = new CheckBox[]{ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9,ck10};
        for (int i = 0; i <subpreguntas.size() ; i++) {
            cardViews[i] = (CardView) rootView.findViewById(idCardViews[i]);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            textViews[i] = (TextView) cardViews[i].findViewById(R.id.formulario_sp_textview);
            editTexts[i] = (EditText) cardViews[i].findViewById(R.id.formulario_sp_edittext);
            layoutSpinners[i] = (LinearLayout) cardViews[i].findViewById(R.id.formulario_sp_spinner_layout);
            spinners[i] = (Spinner) cardViews[i].findViewById(R.id.formulario_sp_spinner);
            edtEspecifiques[i] = (EditText) cardViews[i].findViewById(R.id.formulario_sp_especifique);
            checkBoxes[i] = (CheckBox) cardViews[i].findViewById(R.id.formulario_sp_checkbox);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        cargarDatos();
    }


    public void llenarVista(){
        formularioTitulo.setText(pFormulario.getTitulo());
        for (int i = 0; i <subpreguntas.size() ; i++) {
            final SPFormulario spFormulario = subpreguntas.get(i);
            final EditText editText = editTexts[i];
            final EditText edtEspecifique = edtEspecifiques[i];
            final Spinner spinner = spinners[i];
            final LinearLayout layoutSpinner = layoutSpinners[i];
            cardViews[i].setVisibility(View.VISIBLE);
            textViews[i].setText(spFormulario.getSubpregunta());
            if(!spFormulario.getVar_edittext().equals("")) {
                editText.setVisibility(View.VISIBLE);
                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            ocultarTeclado(editText);
                            layoutSpinner.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
                if(Integer.parseInt(spFormulario.getTipo_edittext()) == TipoInput.TEXTO) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setFilters(new InputFilter[]{
                            new InputFilter.AllCaps(),
                            new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_edittext()))
                    });
                }else{
                    editText.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                    editText.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_edittext()))
                    });
                }
            }else{
                layoutSpinners[i].setVisibility(View.VISIBLE);
                ArrayList<String> ops = daoEncuesta.getOpcionesSpinnerFormulario(spFormulario.getVar_spinner());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,ops);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners[i].setAdapter(adapter);

                if(!spFormulario.getVar_esp_spinner().equals("")){
                    edtEspecifique.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                ocultarTeclado(edtEspecifique);
                                layoutSpinner.requestFocus();
                                return true;
                            }
                            return false;
                        }
                    });
                    if(Integer.parseInt(spFormulario.getTipo_esp_spinner()) == TipoInput.TEXTO) {
                        edtEspecifique.setInputType(InputType.TYPE_CLASS_TEXT);
                        edtEspecifique.setFilters(new InputFilter[]{
                                new InputFilter.AllCaps(),
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }else{
                        edtEspecifique.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                        edtEspecifique.setFilters(new InputFilter[]{
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }
                    spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position == Integer.parseInt(spFormulario.getHab_esp_spinner())){
                                edtEspecifique.setEnabled(true);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_enabled);
                            }else{
                                edtEspecifique.setText("");
                                edtEspecifique.setEnabled(false);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_disabled);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }else edtEspecifique.setVisibility(View.GONE);
            }
            if (!spFormulario.getVar_check_no().equals("")){
                CheckBox checkBox = checkBoxes[i];
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!spFormulario.getVar_edittext().equals("")){
                            if(isChecked){
                                editText.setText("");
                                editText.setEnabled(false);
                                editText.setBackgroundResource(R.drawable.edittext_disabled);
                            }else{
                                editText.setEnabled(true);
                                editText.setBackgroundResource(R.drawable.edittext_enabled);
                            }
                        }else{
                            if(isChecked){
                                spinner.setSelection(0);
                                spinner.setEnabled(false);
                                edtEspecifique.setText("");
                                edtEspecifique.setEnabled(false);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_disabled);
                            }else{
                                spinner.setEnabled(true);
                            }
                        }
                    }
                });
            }
        }
    }

    public void cargarDatos(){
//        DataTablas data = new DataTablas(context);
//        data.open();
//        String valorEdit;
//        String valorEsp;
//        String valorSp;
//        String valorCheck;
//        if(data.existenDatos(getIdTabla(),idEmpresa)){
//            for (int i = 0; i < subpreguntas.size() ; i++){
//                SPFormulario spFormulario = subpreguntas.get(i);
//                if(!spFormulario.getVARE().equals("")){
//                    valorEdit = data.getValor(getIdTabla(),spFormulario.getVARE(),idEmpresa);
//                    if(valorEdit != null) editTexts[i].setText(valorEdit);
//                }else{
//                    valorSp = data.getValor(getIdTabla(),spFormulario.getVARS(),idEmpresa);
//                    if(valorSp != null) spinners[i].setSelection(Integer.parseInt(valorSp));
//                    if(edtEspecifiques[i].getVisibility()==View.VISIBLE) {
//                        valorEsp = data.getValor(getIdTabla(),spFormulario.getVARESP(),idEmpresa);
//                        if(valorEsp != null) edtEspecifiques[i].setText(valorEsp);
//                    }
//                }
//                if(!spFormulario.getVARCK().equals("")){
//                    valorCheck = data.getValor(getIdTabla(),spFormulario.getVARCK(),idEmpresa);
//                    if(valorCheck != null) {if(valorCheck.equals("1"))checkBoxes[i].setChecked(true);}
//                }
//            }
//        }
//        data.close();
    }

    public void guardarDatos(){
//        if (subpreguntas.size() > 0){
//            DataTablas data = new DataTablas(context);
//            data.open();
//            ContentValues contentValues = new ContentValues();
//            for (int i = 0; i < subpreguntas.size(); i++) {
//                SPFormulario spFormulario = subpreguntas.get(i);
//                if(!spFormulario.getVARE().equals(""))
//                    contentValues.put(spFormulario.getVARE(),editTexts[i].getText().toString());
//                else{
//                    contentValues.put(spFormulario.getVARS(),spinners[i].getSelectedItemPosition());
//                    if(edtEspecifiques[i].getVisibility() == View.VISIBLE) contentValues.put(spFormulario.getVARESP(),edtEspecifiques[i].getText().toString());
//                }
//                if(!spFormulario.getVARCK().equals("")) {
//                    if(checkBoxes[i].isChecked())contentValues.put(spFormulario.getVARCK(),1);
//                    else contentValues.put(spFormulario.getVARCK(),0);
//                }
//            }
//            if(!data.existenDatos(getIdTabla(),idEmpresa)){
//                contentValues.put("ID_EMPRESA",idEmpresa);
//                data.insertarValores(getIdTabla(),contentValues);
//            }else data.actualizarValores(getIdTabla(),idEmpresa,contentValues);
//            data.close();
//        }
    }

    public boolean validarDatos(){
        boolean correcto = true;
//        String mensaje = "";
//        int c = 0;
//        while(correcto && c < subpreguntas.size()){
//            if(checkBoxes[c].getVisibility() != View.VISIBLE || !checkBoxes[c].isChecked()){
//                if(editTexts[c].getVisibility() == View.VISIBLE){
//                    if(editTexts[c].getText().toString().trim().equals("")){
//                        correcto = false;
//                        mensaje = "PREGUNTA " + formulario.getTITULO() + "(" + subpreguntas.get(c).getSUBPREGUNTA()+ ")" + ": COMPLETE LA PREGUNTA";
//                    }
//                }else{
//                    if(spinners[c].getSelectedItemPosition() == 0){
//                        correcto = false;
//                        mensaje = "PREGUNTA " + formulario.getTITULO() + "(" + subpreguntas.get(c).getSUBPREGUNTA()+ ")" + ": DEBE SELECCIONAR UNA OPCION";
//                    }else{
//                        if(edtEspecifiques[c].isEnabled() && edtEspecifiques[c].getText().toString().trim().equals("")){
//                            correcto = false;
//                            mensaje = "PREGUNTA " + formulario.getTITULO() + "(" + subpreguntas.get(c).getSUBPREGUNTA()+ ")" + ": DEBE ESPECIFICAR";
//                        }
//                    }
//                }
//            }
//            c++;
//        }
//        if(!correcto) mostrarMensaje(mensaje);
        return correcto;
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
        return pFormulario.getId_tabla();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
