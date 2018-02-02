package com.example.moorg.objetolocalizacao;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by MoorG on 19/07/2017.
 */

public class ObjectLocation extends Activity implements LocationListener{
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean allowNetwork;
    private LocationManager locationManager;
    public LatLng latLng ;

    public ObjectLocation(){


    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(MapsActivity.this,
                    //  "Atenção! Ative o Local...",Toast.LENGTH_LONG).show();

                } else {
                    //  Toast.makeText(MapsActivity.this,
                    //  "Permissão de Localização Negada, ...:(", Toast.LENGTH_LONG).show();
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
        // Esse método onPause é necessário para o android 6.0 >

    }
/*
    public void configLocation(LatLng latLng) {

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(update);
            //    MyLocation myLocation = new MyLocation();
            //   mMap.setLocationSource(myLocation);
            //   myLocation.setLocation(latLng);



        }else{

        }

    }
*/
    @Override
    public void onLocationChanged(Location location) {
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            allowNetwork = false;
        }

        if(allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            latLng = new LatLng(location.getLatitude(), location.getLongitude());


                // Esse Objeto LatLng que está sendo passado por parametro para config colocantion é a posição
                // temos que pega-la
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





    }




