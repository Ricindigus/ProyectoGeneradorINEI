package pe.gob.inei.generadorinei.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.DAOEncuesta;
import pe.gob.inei.generadorinei.model.Encuesta;
import pe.gob.inei.generadorinei.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private TextView tvTituloEncuesta;
    private TextInputEditText edtUsuario;
    private TextInputEditText edtPassword;
    private Button btnIngresar;
    private LinearLayout lytPrincipal;
    private String tituloEncuesta;
    private DAOEncuesta DAOEncuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DAOEncuesta = new DAOEncuesta(this);
        conectarvistas();
        setearTituloEncuesta();
        configurarInputs();


        edtUsuario.setText("ENC0001");
        edtPassword.setText("12345");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos() == true){
                    Usuario usuario = DAOEncuesta.getUsuario(edtUsuario.getText().toString());
                    if(usuario.getClave().equals(edtPassword.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(),MarcoActivity.class);
                        intent.putExtra("idUsuario",usuario.get_id());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Debe ingresar USUARIO y CONTRASEÑA", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void conectarvistas(){
        tvTituloEncuesta = findViewById(R.id.login_tvTituloEncuesta);
        edtUsuario = findViewById(R.id.login_edtUsuario);
        edtPassword = findViewById(R.id.login_edtPassword);
        btnIngresar = findViewById(R.id.login_btIngresar);
    }

    private void setearTituloEncuesta() {
        Encuesta encuesta = DAOEncuesta.getEncuesta();
        tituloEncuesta = encuesta.getTitulo();
        tvTituloEncuesta.setText(tituloEncuesta);
    }
    private void configurarInputs() {
        edtUsuario.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        edtPassword.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
    }
    private boolean validarCampos(){
        boolean valido = true;
        if(edtUsuario.getText().toString().equals("") || edtPassword.getText().toString().equals("")) valido = false;
        return valido;
    }

    @SuppressLint("NewApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea salir de la aplicación?")
                    .setTitle("Aviso")
                    .setCancelable(false)
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Sí",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }




//    @Override
//    protected void onStart() {
//        super.onStart();
//        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
//        lytPrincipal.requestFocus();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
//        lytPrincipal.requestFocus();
//    }
}
