package com.example.moorg.alertmaps.FuncionamentoBackground;

import android.Manifest;
import android.app.Activity;
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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.moorg.alertmaps.BD.DataBase;
import com.example.moorg.alertmaps.MapsActivity;
import com.example.moorg.alertmaps.PontoCerto;
import com.example.moorg.alertmaps.RepositórioTabela;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoorG on 19/07/2017.
 */

public class BackgroundClass extends Activity implements LocationListener {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    private boolean allowNetwork;
    ArrayList []vet = new ArrayList[4];
    SQLiteDatabase conn;
    RepositórioTabela repositórioTabela;
    SQLiteDatabase db;
    LatLng latLngGeral;
    private ArrayList<LatLng> list;

   public BackgroundClass(){
       ActivityCompat.requestPermissions(BackgroundClass.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
               MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

   }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(BackgroundClass.this,
                            "Atenção! Ative o Local...",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(BackgroundClass.this,
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



    public void configLocation(LatLng latLng) {

        latLngGeral = latLng;
        list = new ArrayList<LatLng>();
        list.add(latLng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
      //      mMap.setMyLocationEnabled(true);
            //  map.animateCamera(update);
            BackgroundClass.MyLocation myLocation = new BackgroundClass.MyLocation();
        //    mMap.setLocationSource(myLocation);
            myLocation.setLocation(latLng);

/*
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

*/

        }else{

        }

    }

    public LatLng getLatLng(){
        return latLngGeral;
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

                // numero do BD formatado



                //    if ((latUm.equals(latDo)) && (lngUm.equals(lngDo))) {
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


    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


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
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
