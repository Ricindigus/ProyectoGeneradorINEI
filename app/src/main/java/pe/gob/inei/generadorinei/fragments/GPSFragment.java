package pe.gob.inei.generadorinei.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import pe.gob.inei.generadorinei.model.pojos.componentes.PGps;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GPSFragment extends ComponenteFragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener{

    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;

    private View rootView;
    private CardView cvLongitud, cvAltitud, cvLatitud;
    private ToggleButton btnGPS;
    private TextView txtLatitud, txtLongitud, txtAltitud;
    private Context contexto;
    private String idEmpresa;
    private PGps pGps;


    public GPSFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public GPSFragment(Context contexto, String idEmpresa, PGps pGps) {
        this.contexto = contexto;
        this.idEmpresa = idEmpresa;
        this.pGps = pGps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_gps, container, false);
        cvAltitud = rootView.findViewById(R.id.gps_layout_altitud);
        cvLatitud = rootView.findViewById(R.id.gps_layout_latitud);
        cvLongitud = rootView.findViewById(R.id.gps_layout_longitud);
        txtAltitud = rootView.findViewById(R.id.gps_txt_altitud);
        txtLatitud = rootView.findViewById(R.id.gps_txt_latitud);
        txtLongitud = rootView.findViewById(R.id.gps_txt_longitud);
        btnGPS = rootView.findViewById(R.id.gps_btn_captura);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        cargarDatos();

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLocationUpdates(btnGPS.isChecked());
                if(!btnGPS.isChecked()){
                    txtAltitud.setText("99.999999");
                    txtLatitud.setText("99.999999");
                    txtLongitud.setText("99.999999");
                }
            }
        });

        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(), this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void cargarDatos(){
//        DataTablas data = new DataTablas(contexto);
//        data.open();
//        if(data.existenDatos(getIdTabla(),idEmpresa)){
//            if(!gps.getVARALT().equals("")){
//                String valor = data.getValor(getIdTabla(),gps.getVARALT(),idEmpresa);
//                if(valor != null) txtAltitud.setText(valor);
//            }
//            if(!gps.getVARLAT().equals("")){
//                String valor = data.getValor(getIdTabla(),gps.getVARLAT(),idEmpresa);
//                if(valor != null) txtLatitud.setText(valor);
//            }
//            if(!gps.getVARLONG().equals("")){
//                String valor = data.getValor(getIdTabla(),gps.getVARLONG(),idEmpresa);
//                if(valor != null) txtLongitud.setText(valor);
//            }
//        }
//        data.close();
    }
    public boolean validarDatos(){
        boolean valido = true;
//        if(cvLongitud.getVisibility() == View.VISIBLE && txtLongitud.getText().toString().equals("")) valido = false;
//        if(cvLongitud.getVisibility() == View.VISIBLE && txtLongitud.getText().toString().equals("")) valido = false;
//        if(cvLongitud.getVisibility() == View.VISIBLE && txtLongitud.getText().toString().equals("")) valido = false;
//        if(!valido) mostrarMensaje("DEBE CAPTURAR LAS COORDENADAS GPS");
        return valido;
    }

    @Override
    public void habilitar() {
        rootView.setVisibility(View.VISIBLE);
    }

    public void guardarDatos(){
//        DataTablas data = new DataTablas(contexto);
//        data.open();
//        ContentValues contentValues = new ContentValues();
//        if(!gps.getVARLAT().equals("")) contentValues.put(gps.getVARLAT(), txtLatitud.getText().toString());
//        if(!gps.getVARLONG().equals("")) contentValues.put(gps.getVARLONG(), txtLongitud.getText().toString());
//        if(!gps.getVARALT().equals("")) contentValues.put(gps.getVARALT(), txtAltitud.getText().toString());
//        if(!data.existenDatos(getIdTabla(),idEmpresa)){
//            contentValues.put("ID_EMPRESA",idEmpresa);
//            data.insertarValores(getIdTabla(),contentValues);
//        }else data.actualizarValores(getIdTabla(),idEmpresa,contentValues);
//        data.close();
    }
    public void llenarVista(){
        if(pGps.getVar_alt().equals("")) cvAltitud.setVisibility(View.GONE);
        if(pGps.getVar_lat().equals("")) cvLatitud.setVisibility(View.GONE);
        if(pGps.getVar_long().equals("")) cvLongitud.setVisibility(View.GONE);
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
    public void inhabilitar(){
        rootView.setVisibility(View.GONE);
    }

    @Override
    public boolean estaHabilitado(){
        boolean habilitado = false;
        if(rootView.getVisibility() == View.VISIBLE) habilitado = true;
        return habilitado;
    }

    public String getIdTabla(){
        return pGps.getId_tabla();
    }

    private void toggleLocationUpdates(boolean enable) {
        if (enable) {
            enableLocationUpdates();
        } else {
            disableLocationUpdates();
        }
    }

    private void enableLocationUpdates() {
        locRequest = new LocationRequest();
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locRequest).build();
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(getActivity(), PETICION_CONFIG_UBICACION);
                            btnGPS.setChecked(false);
                        } catch (IntentSender.SendIntentException e) {
                            btnGPS.setChecked(false);
                            Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        btnGPS.setChecked(false);
                        break;
                }
            }
        });
    }

    private void disableLocationUpdates() {
        try{
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
        }catch (Exception e){}

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.
            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locRequest, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.
        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
//            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            txtLatitud.setText(String.valueOf(loc.getLatitude()));
            txtLongitud.setText(String.valueOf(loc.getLongitude()));
            txtAltitud.setText(String.valueOf(loc.getAccuracy()));
        } else {
            txtLatitud.setText("99.999999");
            txtLongitud.setText("99.999999");
            txtAltitud.setText("99.999999");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido
                @SuppressWarnings("MissingPermission")
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        btnGPS.setChecked(false);
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOGTAG, "Recibida nueva ubicación!");
        disableLocationUpdates();
        btnGPS.setChecked(false);
        //Mostramos la nueva ubicación recibida
        updateUI(location);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        apiClient.stopAutoManage(getActivity());
        apiClient.disconnect();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
