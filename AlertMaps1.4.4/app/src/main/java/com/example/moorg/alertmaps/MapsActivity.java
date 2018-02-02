package com.example.moorg.alertmaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.moorg.alertmaps.BD.DataBase;
import com.example.moorg.alertmaps.Menu.menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;

public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback,  GoogleApiClient.ConnectionCallbacks  {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private GoogleMap map;
    private Marker marker;
    private ArrayList<LatLng> list;
    private ArrayAdapter<String> adpDados;
    private ArrayList<Double> recebeLatLng;
    private boolean allowNetwork;
    private LocationManager locationManager;
    View mapView;
    View mapCompass;

    ArrayList []vet = new ArrayList[4];

    double auxLat, auxLng;
    int cont;
     SQLiteDatabase conn;
     RepositórioTabela repositórioTabela;
    SQLiteDatabase db;

    /* várias da procura de endreço */
    EditText etAddress;
    ImageButton btNameToCoord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

       etAddress = (EditText) findViewById(R.id.et_address);
        btNameToCoord = (ImageButton) findViewById(R.id.bt_name_to_coord);

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsActivity.this,
                            "Atenção! Ative o Local...",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MapsActivity.this,
                            "Permissão de Localização Negada, ...:(", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        allowNetwork = true;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent it = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);
        } else {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }else{

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        map = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        list = new ArrayList<LatLng>();

        LatLng sydney = new LatLng(-34, 151);

        LatLng Rio = new LatLng(-23.2301558,-42.9121089);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rio,0));

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,20,30);


            View compassButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            RelativeLayout.LayoutParams layoutParamsCompass = (RelativeLayout.LayoutParams)
                    compassButton.getLayoutParams();

            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            layoutParamsCompass.setMargins(20,0,0,30);


            //    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)locationButton.getLayoutParams();

/*
            layoutParams.rightToRight = 16;
            layoutParams.bottomToBottom = 16;
            layoutParams.leftToLeft = 16;
            layoutParams.topToTop = 16;
            layoutParams.width = 58;

            layoutParams.height = 58;

            layoutParams.setMargins(8,8,8,8);

            */
        }


        LatLng PERTH = new LatLng(-31.90,115.86);
        LatLng latLng = new LatLng(22,42);


        try {
            DataBase dataBase = new DataBase(this);

            conn = dataBase.getWritableDatabase();
            repositórioTabela = new RepositórioTabela(conn);

           //   repositórioTabela.buscaTabelaLatLng(this);
           vet = repositórioTabela.buscaTabelaLatLng(this);

            ArrayList auxList = vet[1];
            ArrayList auxList2 = vet[2];

            for (int i= 0;i<auxList.size();i++){

                double auxLatN = (double)auxList.get(i);
                double auxLngN = (double)auxList2.get(i);
                customAddMarker(new LatLng(auxLatN,auxLngN));
            }

        } catch (SQLException EX) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro" + EX.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();

        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("Script", "setOnMapClickListener()");
          /*      if(marker != null){
                    marker.remove();
                } */



                customAddMarker(new LatLng(latLng.latitude, latLng.longitude));
                list.add(latLng);

                View view = null;

                  getLocation(view);


                //   drawRoute();

            }
        });
    //    configLocation(latLng); Retirei essa linha pois ela adicionava o ponto no espirito santo

    }

    public void customAddMarker(LatLng latLng ){

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);

        marker = mMap.addMarker(options);


        // Lembrar de fazer o DELETE MARKER NESSA CLASS
    }

    public void getLocation(View view){
        // https://developer.android.com/reference/android/location/Address.html

        Geocoder gc = new Geocoder(MapsActivity.this);

        List<Address> addressList;
        try {
            addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
            //  addressList = gc.getFromLocationName("Rua Vergueiro, Sãoo Paulo, São Paulo, Brasil", 1);

            String address = "Endereço: " + addressList.get(0).getThoroughfare() + ", " ; // Rua
         //   address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
            address += "" + addressList.get(0).getFeatureName() + ". \n";   // Numero
            address += "Bairro: " + addressList.get(0).getSubLocality() + ";\n"; // Bairro
            address += "" + addressList.get(0).getLocality() + " ";
            address += ", " + addressList.get(0).getAdminArea() + " ";
            address += ", " + addressList.get(0).getCountryName() + ".";
           // address += "Latitude: " + addressList.get(0).getLatitude() + "\n";
           // address += "Longitude: " + addressList.get(0).getLongitude() + "\n";
           // address += "Bairro: " + addressList.get(0).getSubLocality() + "\n"; // Bairro

            //  ListaTarefas listaTarefas = new ListaTarefas();
            //  listaTarefas.markDado = "kkkk";

            String lat = "" + addressList.get(0).getLatitude();
            String lng = "" + addressList.get(0).getLongitude();
            // DATA BASE 1

            repositórioTabela = new RepositórioTabela(conn);


            repositórioTabela.testeInserirContatos(address,lat,lng);




            Toast.makeText(MapsActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();
            //  Toast.makeText(MapsActivity.this, "LatLng: "+ll, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void configLocation(LatLng latLng) {
        list = new ArrayList<LatLng>();
        list.add(latLng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            //  map.animateCamera(update);
            MyLocation myLocation = new MyLocation();
            mMap.setLocationSource(myLocation);
            myLocation.setLocation(latLng);


            Geocoder gc = new Geocoder(MapsActivity.this);

            List<Address> addressList;
            try {
                addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
                String address = "Endereço: " + addressList.get(0).getThoroughfare() + ", " ; // Rua
                //   address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
                address += "" + addressList.get(0).getFeatureName() + ". \n";   // Numero
                address += "Bairro: " + addressList.get(0).getSubLocality() + ";\n"; // Bairro
                address += "" + addressList.get(0).getLocality() + " ";
                address += ", " + addressList.get(0).getAdminArea() + " ";
                address += ", " + addressList.get(0).getCountryName() + ".";


                metodoCentral(addressList.get(0).getLatitude(),addressList.get(0).getLongitude(),address);

            //    Toast.makeText(MapsActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }else{

        }

    }
    public void irParaMenu(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),menu.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(intentAux1);
    }


    public void irParaTarefas(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),ListaTarefas.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(intentAux1);
    }


    // esse método tem que rodar em segundo plano
    @Override
    public void onLocationChanged(Location location) {
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            allowNetwork = false;
        }

        if(allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            configLocation(new LatLng(location.getLatitude(), location.getLongitude())); // método ainda não implementado
        }
    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

// esse método tem que rodar em segundo plano
    public void metodoCentral(double lat, double lng, String endereco){


        try {
            DataBase dataBase = new DataBase(this);

            conn = dataBase.getWritableDatabase();
            repositórioTabela = new RepositórioTabela(conn);
            vet = repositórioTabela.buscaTabelaLatLng(this);
            ArrayList auxList = vet[1];
            ArrayList auxList2 = vet[2];
            DecimalFormat df = new DecimalFormat("00.000");
            String latUm = df.format(lat);
            String lngUm = df.format(lng);
            // latUm e Lat 2 foram estao com o número do GPS formatados
            for (int i= 0;i<auxList.size();i++) {

                double auxLatN = (double) auxList.get(i);
                double auxLngN = (double) auxList2.get(i);
                String auxLatUm = df.format(auxLatN);
                String auxLngDo = df.format(auxLngN);

                if((latUm.equals(auxLatUm))&&(lngUm.equals(auxLngDo))){

                    repositórioTabela.excluir(auxLatN);
                    Intent intentAux1 = new Intent(getApplicationContext(), PontoCerto.class);
                    // aux.setClass(this,Calculadora.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("endereco",endereco);
                    intentAux1.putExtras(bundle);
                    startActivity(intentAux1);
                }
            }
        } catch (SQLException EX) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro" + EX.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    public class MyLocation implements LocationSource {
        private OnLocationChangedListener listener;

        @Override
        public void activate(OnLocationChangedListener listener) {
            this.listener = listener;
            Log.i("Script", "activate()");
        }

        @Override
        public void deactivate() {
            Log.i("Script", "deactivate()");
        }


        public void setLocation(LatLng latLng){
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);

            if(listener != null){
                listener.onLocationChanged(location);
            }
        }
    }


    // -----------------------------       aqui começa o teste de procurar endereço     ---------------------------------------
   // LISTERNERS


    @Override
    public void onConnected(Bundle bundle) {
        /*
        Log.i("LOG", "AddressLocationActivity.onConnected(" + bundle + ")");

         /*   Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(l != null){
        //        mLastLocation = l;
                btNameToCoord.setEnabled(true);
                btCoordToName.setEnabled(true);
            }
                */

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void getLocationListener(View view){
        int type;
        String address = null;

        if(view.getId() == R.id.bt_name_to_coord){
            type = 1;
            address = etAddress.getText().toString();
        }
        else{
            type = 2;
        }

        procuraEndereço(type, address);
    }

/*
    public void onEvent(final MessageEB m){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
           //     Log.i("LOG", m.getResultMessage());
              //  tvAddressLocation.setText("Data: "+m.getResultMessage());
            }
        });
    }
*/

    public void procuraEndereço(int type, String address ){
       // Location location = intent.getParcelableExtra(AddressLocationActivity.LOCATION);
       // type = intent.getIntExtra(AddressLocationActivity.TYPE, 1);
       // address = intent.getStringExtra(AddressLocationActivity.ADDRESS);

        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String error = "";
        String resultAddress = "";


        try {
            if(type == 2 || address == null) {
                // list = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            }
            else{
                list = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            error = "Network problem";
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            error = "Illegal arguments";
        }


        if(list != null && list.size() > 0){
            Address a = list.get(0);


            if(type == 2 || address == null){
                for(int i = 0, tam = a.getMaxAddressLineIndex(); i < tam; i++){
                    resultAddress += a.getAddressLine(i);
                    resultAddress += i < tam - 1 ? ", " : "";
                }
            }
            else{
                resultAddress += a.getLatitude()+"\n";
                resultAddress += a.getLongitude();

                double auxLat = a.getLatitude();
                double auxLng = a.getLongitude();

                String addressN = "Endereço: " + a.getThoroughfare() + ", " ; // Rua
                //   address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
                addressN += "" + a.getFeatureName() + ". \n";   // Numero
                addressN += "Bairro: " + a.getSubLocality() + ";\n"; // Bairro
                addressN += "" + a.getLocality() + " ";
                addressN += ", " + a.getAdminArea() + " ";
                addressN += ", " + a.getCountryName() + ".";

                LatLng latLng = new LatLng(auxLat,auxLng);
                customAddMarker(latLng);
                repositórioTabela = new RepositórioTabela(conn);

                String auxLatS = ""+a.getLatitude();
                String auxLngS = ""+a.getLongitude();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                repositórioTabela.testeInserirContatos(addressN,auxLatS,auxLngS);
            }
        }
        else{
            resultAddress = error;
        }




    }
}



