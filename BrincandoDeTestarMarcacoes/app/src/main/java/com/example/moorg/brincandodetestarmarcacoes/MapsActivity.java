package com.example.moorg.brincandodetestarmarcacoes;

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
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moorg.brincandodetestarmarcacoes.BD.DataBase;
import com.example.moorg.brincandodetestarmarcacoes.BD.DataBaseLatLng;
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
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker marker;
    private ArrayList<LatLng> list;
    private ArrayAdapter<String> adpDados;
    private ArrayList<Double> recebeLatLng;

    int cont;
    private SQLiteDatabase conn;
    private RepositórioTabela repositórioTabela;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        list = new ArrayList<LatLng>();

        LatLng sydney = new LatLng(-34, 151);
        LatLng Rio = new LatLng(-43.2301558, -22.9121089);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rio, 0));
        LatLng PERTH = new LatLng(-31.90, 115.86);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
       // configLocation(LatLng latLng);



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

              //  getLocation(view);


                //   drawRoute();

            }
        });


    }

/*
    @Override
    public void onResume() {
        super.onResume();

        allowNetwork = true;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);

        }else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager(this);
    }

    public void configLocation(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


        }
        mMap.setMyLocationEnabled(true);

        //

        mMap.animateCamera(update);
        Main_GPS_Activity.MyLocation myLocation = new Main_GPS_Activity.MyLocation();
        mMap.setLocationSource(myLocation);
        myLocation.setLocation(latLng);


    }








    public class myLocation implements LocationSource{
        private OnLocationChangedListener listener;

        @Override
        public void activate(OnLocationChangedListener onLocationChangedListener) {
            this.listener = listener;
        }

        @Override
        public void deactivate() {

        }

        public void setLocation(LatLng latLng){
            Location location = new Location(LocationManager.GPS_PROVIDER);

            listener.onLocationChanged(arg0);
        }
    }

*/

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

            String address = "Rua: " + addressList.get(0).getThoroughfare() + "\n"; // Rua
            address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
            address += "Numero: " + addressList.get(0).getFeatureName() + "\n";   // Numero
            address += "Cidade: " + addressList.get(0).getLocality() + "\n";
            address += "Estado: " + addressList.get(0).getAdminArea() + "\n";
            address += "Pais: " + addressList.get(0).getCountryName() + "\n";
            address += "Latitude: " + addressList.get(0).getLatitude() + "\n";
            address += "Longitude: " + addressList.get(0).getLongitude() + "\n";
            address += "Bairro: " + addressList.get(0).getSubLocality() + "\n"; // Bairro

            //  ListaTarefas listaTarefas = new ListaTarefas();
            //  listaTarefas.markDado = "kkkk";


            // DATA BASE 1

                repositórioTabela = new RepositórioTabela(conn);
                for (long i = 100; i <= 500; i++) {
                  //  repositórioTabela.excluir(i);
                }
                   repositórioTabela.testeInserirContatos(address);
                    repositórioTabela.buscaTabela(this);
/*
               repositórioTabela.InserirLat(addressList.get(0).getLatitude());
                repositórioTabela.InserirLng(addressList.get(0).getLongitude());
                ArrayList recebeLat =    repositórioTabela.buscaTabelaLat(this);
                ArrayList recebeLng =    repositórioTabela.buscaTabelaLng(this);

            double[] vetorLat = new double[10];
            double[] vetorLng = new double[10];
*/
            /*
            int j = 0;
            for (Object i : recebeLat){
                j = j+1;
                Double aux1 = (Double)i;
                vetorLat[j] = aux1;
            }
            j=0;
            for (Object i : recebeLng){
                j=j+1;
                Double aux2 = (Double)i;
                vetorLng[j] = aux2;
            }
*/
            // O erro está aqui em baixo, provavelmente criar um objeto várias vezes
        //    LatLng latLng = new LatLng(vetorLat[1],vetorLng[1]);
           //   for (int i=1; i<=10;i++){



                //  mMap.addMarker(new MarkerOptions().position(latLng)); // Temos que colocar isso aqui no on create dessa classe

            //  }


                //  ArrayList auxLat =  repositórioTabela.buscaTabelaLat(this);
                //  ArrayList auxLng = repositórioTabela.buscaTabelaLng(this);


                //    for (Object i : auxLat){
                //       int x = (int) i;

                //   }


                // Data Base 2




                //   AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                //    dlg.setMessage("Conexão criada com Sucesso");
                //  dlg.setNeutralButton("Ok",null);
                //  dlg.show();






            //   metodoCentral(addressList);


            //    LatLng ll = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

           Toast.makeText(MapsActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();
            //  Toast.makeText(MapsActivity.this, "LatLng: "+ll, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }






    }



/*

    public void metodoCentral(List<Address> addressList){ // esse método faz a lógica de encontrar o local e direcionar para a tela do aviso
        int aux = (int)addressList.get(0).getLatitude();
        int aux2 = (int)addressList.get(0).getLongitude();

        /* Fazendo casting com float e escolhendo o numero de casa decimais

                float teste = 2341.237843F;
                DecimalFormat df = new DecimalFormat("0.00");  // aqui vc escolhe quantas casas decimnais quer
                String aux = df.format(teste);
                a função vai devolver uma string


         *//*
        if ((aux == -22)&&(aux2 == -43)){
            LatLng Rio = new LatLng(0,0);
            // marker = mMap.addMarker(Rio);
          //  mMap.addMarker(new MarkerOptions().title("CRL PORRA").snippet("Deu certo.").position(Rio));

         //   irParaPontoCerto();
            Intent aux3 = new Intent(getApplicationContext(),PontoCerto.class);
            startActivity(aux3);
        }
    }


    public void irParaTarefas(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),ListaTarefas.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(intentAux1);
    }
*/


    /*
    public void getLocation(View view){
        Geocoder gc = new Geocoder(MapsActivity.this);


        try {
            addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
            //  addressList = gc.getFromLocationName("Rua Vergueiro, Sãoo Paulo, São Paulo, Brasil", 1);

            String address = "Rua: "+addressList.get(0).getThoroughfare()+"\n";
            address += "Postal Code: "+addressList.get(0).getPostalCode()+"\n";
            address += "Postal Code: "+addressList.get(0).getFeatureName()+"\n";
            address += "Cidade: "+addressList.get(0).getSubAdminArea()+"\n";
            address += "Estado: "+addressList.get(0).getAdminArea()+"\n";
            address += "Pais: "+addressList.get(0).getCountryName()+"\n";
            address += "Latitude: "+addressList.get(0).getLatitude()+"\n";
            address += "Longitude: "+addressList.get(0).getLongitude()+"\n";




            //    LatLng ll = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

            Toast.makeText(MapsActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();
            //  Toast.makeText(MapsActivity.this, "LatLng: "+ll, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
     */


/*
    public (){
        // Pegando os dados da DataBase
        DataBase dataBase = new DataBase(this);
        conn = dataBase.getReadableDatabase();

        ArrayList <String>aux = new ArrayList<String>();

        Cursor cursor  =  conn.query(Contato.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0 ) {

            cursor.moveToFirst();

            do {



                String nome = cursor.getString(1);
                aux.add(nome);

              //  Contato contato = new Contato();
              //  contato.setId( cursor.getLong( cursor.getColumnIndex(Contato.ID) ) );
               // contato.setNome( cursor.getString( cursor.getColumnIndex(Contato.NOME ) ) );
                //    adpContatos.add(contato);

            }while (cursor.moveToNext());

        }
    }*/





}
