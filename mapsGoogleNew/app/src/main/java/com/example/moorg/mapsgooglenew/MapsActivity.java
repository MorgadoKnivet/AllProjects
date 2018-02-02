package com.example.moorg.mapsgooglenew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.duration;
import static com.example.moorg.mapsgooglenew.R.attr.title;
import static com.example.moorg.mapsgooglenew.R.id.text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Polyline polyline;
    private ArrayList<LatLng> list;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // ele refencia o XML
        mapFragment.getMapAsync(this); // cria o Mapa chamando a classe onMapReady
        db = openOrCreateDatabase("TesteDB", Context.MODE_PRIVATE, null);
     //   db.execSQL("CREATE TABLE IF NOT EXISTS Contatos (Nome VARCHAR);");
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

        // função abaixo coloca o ponto escolhido por código e da para arrastar pressionando
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        list = new ArrayList<LatLng>();

        LatLng sydney = new LatLng(-34, 151);
        LatLng Rio = new LatLng(-43.2301558,-22.9121089);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rio,0));
        LatLng PERTH = new LatLng(-31.90,115.86);
      //  Marker perth = mMap.addMarker(new MarkerOptions().position(PERTH).draggable(true).flat(true));
        // Draggable permite que o marcador mude de lugar, flat permite que o marcador mude de tamanho com o zoom
    //    Marker rio = mMap.addMarker(new MarkerOptions().position(Rio).draggable(true).title("Era para ser o rio"));

        // mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        // -43.2301558!2d-22.9121089

     //   mMap.addMarker(new MarkerOptions().position(Rio).title("Marker in Rio de Janeiro"));

        // biblioteca Marker https://developers.google.com/maps/documentation/android-api/marker?hl=pt-br
        // tutorial aprofundado youtube versão 2014 https://www.youtube.com/watch?v=GMbk5Qy1H34&index=3&list=PLBA57K2L2RIKWuo6d7LdOeS1u0rQ_RBPA
        // estudamos pouco sobre





        /* animação da camera, o aplicativo abre no zoom 0, ele anima indo automaticamente até zoom 10
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Log.i("Script", "GoogleMap.CancelableCallback.onCancel()");
            }

            @Override
            public void onCancel() {
                Log.i("Script", "GoogleMap.CancelableCallBack.onFinish()");

            }
        });
         animação termina aqui */
// página oficial sobre marks
// https://developers.google.com/android/reference/com/google/android/gms/maps/model/MarkerOptions
// funções abaixo vão colocar o ponto vermelho com 1 click do mouse
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("Script", "setOnMapClickListener()");
                if(marker != null){
                    marker.remove();
                }
                customAddMarker(new LatLng(latLng.latitude, latLng.longitude), "2: Marcador Alterado", "O Marcador foi reposicionado");
                list.add(latLng);
             //   drawRoute();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("Script", "3: Marker: "+marker.getTitle());
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("Script", "4: Marker: "+marker.getTitle());
            }
        });
    }








    public void customAddMarker(LatLng latLng, String title, String snippet ){
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).title(title).snippet(snippet).draggable(true);

        marker = mMap.addMarker(options);
    }


/*

// desenhar a distancia entre pontos
   // necessário aprender video anterior
    // https://www.youtube.com/watch?v=sORak6tRyoY&index=4&list=PLBA57K2L2RIKWuo6d7LdOeS1u0rQ_RBPA
    public void drawRoute(){
        PolylineOptions po;
        if(polyline == null){
            po = new PolylineOptions();
            for (int i = 0, tam = list.size();i< tam;i++ ){
                po.add(list.get(i));

            }
            po.color(Color.BLACK);
            polyline = mMap.addPolyline(po);
        }
        else{
            polyline.setPoints(list);
        }
    }





    public void getDistance(View view){
        double distance = 0;

        for(int i = 0, tam = list.size(); i < tam; i++){
            if(i < tam - 1){
                distance += distance(list.get(i), list.get(i+1));
            }
        }

        Toast.makeText(MapsActivity.this, "Distancia: "+distance+" metros", Toast.LENGTH_LONG).show();
    }


//metodo para calcular a distancia, by: stackoverflow
    public static double distance(LatLng StartP, LatLng EndP) {
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6366000 * c;
    }
*/


   public void getLocation(View view){
        Geocoder gc = new Geocoder(MapsActivity.this);

        List<Address> addressList;
        try {
            addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
          //  addressList = gc.getFromLocationName("Rua Vergueiro, Sãoo Paulo, São Paulo, Brasil", 1);

            String address = "Rua: "+addressList.get(0).getThoroughfare()+"\n";
            address += "Cidade: "+addressList.get(0).getSubAdminArea()+"\n";
            address += "Estado: "+addressList.get(0).getAdminArea()+"\n";
            address += "Pais: "+addressList.get(0).getCountryName();
            address += "Latitude: "+addressList.get(0).getLatitude();
            address += "Longitude: "+addressList.get(0).getLongitude();

        //    LatLng ll = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

            Toast.makeText(MapsActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();
         //  Toast.makeText(MapsActivity.this, "LatLng: "+ll, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
 /*       double distance = 0;
        for (int i = 0, tam = list.size(); i < tam; i++) {
            distance +=  distance(list.get(i),list.get(i+1));
        }
        Toast.makeText(MapsActivity.this, "Distancia: " +distance+" metros", Toast.LENGTH_LONG).show();
    }
*/



}

