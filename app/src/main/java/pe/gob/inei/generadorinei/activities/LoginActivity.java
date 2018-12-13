package pe.gob.inei.generadorinei.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.Data;
import pe.gob.inei.generadorinei.model.Encuesta;
import pe.gob.inei.generadorinei.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private TextView tvTituloEncuesta;
    private TextInputEditText tvUsuario;
    private TextInputEditText tvPassword;
    private Button btnIngresar;
    private LinearLayout lytPrincipal;
//    private DataComponentes dataComponentes;
    private String tituloEncuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvTituloEncuesta = (TextView) findViewById(R.id.login_tvTituloEncuesta);
        tvUsuario = (TextInputEditText) findViewById(R.id.login_etUsuario);
        tvPassword = (TextInputEditText) findViewById(R.id.login_etPassword);
        btnIngresar = (Button) findViewById(R.id.login_btIngresar);
        Data data = new Data(this);
        data.open();
        Encuesta encuesta = data.getEncuesta();
        tituloEncuesta = encuesta.getTitulo();
        tvTituloEncuesta.setText(tituloEncuesta);
        tvUsuario.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        tvPassword.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});

        tvUsuario.setText("OPER01");
        tvPassword.setText("123456");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos() == true){
                    Usuario usuario = new Usuario();
                    Data data = new Data(LoginActivity.this);
                    data.open();
                    usuario = data.getUsuario(tvUsuario.getText().toString());
                    data.close();
                    if(usuario.getClave().equals(tvPassword.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(),MarcoActivity.class);
                        intent.putExtra("idUsuario",usuario.get_id());
                        intent.putExtra("permisoUsuario",usuario.getCargo_id());
                        intent.putExtra("tituloEncuesta",tituloEncuesta);
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

    @Override
    protected void onStart() {
        super.onStart();
        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
        lytPrincipal.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
        lytPrincipal.requestFocus();
    }

    public boolean validarCampos(){
        boolean valido = true;
        if(tvUsuario.getText().toString().equals("") || tvPassword.getText().toString().equals("")) valido = false;
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
}
