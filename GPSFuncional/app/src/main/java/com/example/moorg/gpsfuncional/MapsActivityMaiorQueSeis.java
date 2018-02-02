package com.example.moorg.gpsfuncional;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;


        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.GoogleMapOptions;
        import com.google.android.gms.maps.LocationSource;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.GoogleMap.CancelableCallback;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.maps.model.Polyline;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.Menu;
        import android.widget.Toast;

/**
 * Created by MoorG on 20/05/2017.
 */

public class MapsActivityMaiorQueSeis extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private ArrayList<LatLng> list;
    private SupportMapFragment mapFrag;
    private GoogleMap map;
    private LocationManager locationManager;
    private boolean allowNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        GoogleMapOptions options = new GoogleMapOptions();
        options.zOrderOnTop(true);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        ActivityCompat.requestPermissions(MapsActivityMaiorQueSeis.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsActivityMaiorQueSeis.this,
                            "Atenção! Ative o Local...",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MapsActivityMaiorQueSeis.this,
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
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        list = new ArrayList<LatLng>();
        LatLng latLng = new LatLng(-20.230521, -40.314816);





        configLocation(latLng);


    }

    public void configLocation(LatLng latLng) {
        list = new ArrayList<LatLng>();
        list.add(latLng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            map.setMyLocationEnabled(true);
          //  map.animateCamera(update);
            MyLocation myLocation = new MyLocation();
            map.setLocationSource(myLocation);
            myLocation.setLocation(latLng);

            Geocoder gc = new Geocoder(MapsActivityMaiorQueSeis.this);

            List<Address> addressList;
            try {
                addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
                String address = "Rua: " + addressList.get(0).getThoroughfare() + "\n"; // Rua
                address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
                address += "Numero: " + addressList.get(0).getFeatureName() + "\n";   // Numero
                address += "Cidade: " + addressList.get(0).getLocality() + "\n";
                address += "Estado: " + addressList.get(0).getAdminArea() + "\n";
                address += "Pais: " + addressList.get(0).getCountryName() + "\n";
                address += "Latitude: " + addressList.get(0).getLatitude() + "\n";
                address += "Longitude: " + addressList.get(0).getLongitude() + "\n";
                address += "Bairro: " + addressList.get(0).getSubLocality() + "\n";

                Toast.makeText(MapsActivityMaiorQueSeis.this, "Local: "+address, Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


       // linha de teste para saber se estar entrando aqui     map.addMarker(new MarkerOptions().position(latLng));

        }else{

        }






    }

    public class MyLocation implements LocationSource{
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

    @Override
    public void onLocationChanged(Location location) {
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            allowNetwork = false;
        }

        if(allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            configLocation(new LatLng(location.getLatitude(), location.getLongitude()));
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

