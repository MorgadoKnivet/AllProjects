package com.example.moorg.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity_GPS extends AppCompatActivity {
    private TextView idLatitude, idLongitude, idPais, idCidade, idEstado;
    private Location location;
    private LocationManager locationManager;
    private Address endereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gps);

        idLatitude = (TextView)findViewById(R.id.idLatitude);
        idLongitude = (TextView)findViewById(R.id.idLatitude);
        idCidade = (TextView)findViewById(R.id.idCidade);
        idEstado = (TextView)findViewById(R.id.idEstado);
        idPais = (TextView)findViewById(R.id.idPais);

        double latitude = 0.0;
        double longitude = 0.0;
        // A partir de uma att do android o usuário pode mudar permissões, então precisamos veríficar se a perfissão continua válida

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED) {
            //solicitar permissão
        }else{
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }
        if(location!= null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }
        idLongitude.setText("Longitude: "+ longitude);
        idLatitude.setText("Latitude: "+ latitude);

        try{
            endereco = buscarEndereco(latitude, longitude);

            idCidade.setText("Cidade: "+ endereco.getLocality());
            idEstado.setText("Estado "+ endereco.getAdminArea());
            idPais.setText("País: "+ endereco.getCountryName());
        } catch (IOException e){
            Log.i("GPS", e.getMessage());
        }

    }
    public Address buscarEndereco(double latitude, double longitude) throws IOException{
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude,longitude,1);
        if (addresses.size() > 0){
            address = addresses.get(0);
        }
        return  address;

    }
}


