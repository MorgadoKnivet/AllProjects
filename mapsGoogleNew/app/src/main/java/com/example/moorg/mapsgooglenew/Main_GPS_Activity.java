package com.example.moorg.mapsgooglenew;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

/**
 * Created by MoorG on 02/04/2017.
 */

public class Main_GPS_Activity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private SupportMapFragment mapFrag;
    private GoogleMap mMap;
    private ArrayList<LatLng> list;
    private LocationManager locationManager;
    private boolean allowNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        GoogleMapOptions options = new GoogleMapOptions();
        options.zOrderOnTop(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // ele refencia o XML
        mapFragment.getMapAsync(this); // cria o Mapa chamando a classe onMapReady

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // função abaixo coloca o ponto escolhido por código e da para arrastar pressionando
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        list = new ArrayList<LatLng>();
        LatLng latLng = new LatLng(-22.890521, -43.314816);
        configLocation(latLng);
        //  LatLng sydney = new LatLng(-34, 151);
        // LatLng Rio = new LatLng(-23.2301558, -43.9121089);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rio, 0));
        // LatLng PERTH = new LatLng(-31.90, 115.86);
        //  configLocation(Rio);
    }

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

    private void locationManager(Main_GPS_Activity main_gps_activity) {
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
        mMap.animateCamera(update);
        MyLocation myLocation = new MyLocation();
        mMap.setLocationSource(myLocation);
        myLocation.setLocation(latLng);

// PQ se esse código estiver funcionando, é puxar um IF, de modo que ele encontre a localidade do colégio dela, quando encontrar, vamos mudar para um outro layout aonde vai estar escrito uma mensagem bonitinha
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            allowNetwork = false;
        }
        if (allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            configLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }


    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    public static class MyLocation implements LocationSource{
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
}
